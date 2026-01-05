package com.jefiro.thaurus_cnc.service;


import com.jefiro.thaurus_cnc.Notificador.publisher.BuscarPedidoPendentePublisher;
import com.jefiro.thaurus_cnc.Notificador.publisher.PedidoPublisher;
import com.jefiro.thaurus_cnc.dto.RelatorioMensalDTO;
import com.jefiro.thaurus_cnc.dto.cliente.ClienteResponse;
import com.jefiro.thaurus_cnc.dto.cliente.ClienteUpdate;
import com.jefiro.thaurus_cnc.dto.pedido.*;
import com.jefiro.thaurus_cnc.infra.exception.DadosInvalidosException;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.pagamento.Pagamentos;
import com.jefiro.thaurus_cnc.model.*;
import com.jefiro.thaurus_cnc.repository.PagamentoRepository;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import com.jefiro.thaurus_cnc.repository.VarianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoPublisher pedidoPublisher;
    @Autowired
    private BuscarPedidoPendentePublisher buscarPedidoPendentePublisher;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private VarianteRepository varianteRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private PedidoItemService pedidoItemService;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PedidoResponse newPedido(NewPedido pedido) {
        if (pedido.cliente() == null || pedido.itens() == null) {
            throw new DadosInvalidosException();
        }
        ClienteResponse cliente = null;

        try {
            cliente = new ClienteResponse(clienteService.findByRemoteJid(pedido.cliente().remoteJid()));
            cliente = clienteService.update(cliente.id(), new ClienteUpdate(pedido.cliente()));
        } catch (Exception e) {
            cliente = clienteService.novo(pedido.cliente());
        }

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setCliente(clienteService.findByRemoteJid(cliente.remoteJid()));

        List<PedidoItem> itens = new ArrayList<>();


        for (PedidoItemDTO newItem : pedido.itens()) {
            Produto produto = produtoService.get(newItem.produto_id());

            Variante variante = varianteRepository.findById(newItem.variante_id()).orElseThrow(RecursoNaoEncontradoException::new);


            PedidoItem item = new PedidoItem(produto, variante, newItem.personalizacao(), newItem.quantidade());
            item.setPedido(pedidoEntity);
            itens.add(item);
        }

        pedidoEntity.setItens(itens);
        pedidoEntity.setFrete(pedido.frete());
        pedidoEntity.setValor_total((itens.stream().mapToDouble(c -> c.getVariante().getValor()).sum()) + pedido.frete().valor_frete());

        Pagamentos pagamentos = pagamentoRepository.save(new Pagamentos());

        pedidoEntity.setPagamentos(pagamentos);

        pedidoEntity = pedidoRepository.save(pedidoEntity);

        pedidoPublisher.publishPedidoCriado(pedidoEntity);

        return new PedidoResponse(pedidoEntity);
    }

    public Pedido upSimples(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void updatePedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Page<PedidoCardView> listar(Pageable pageable) {
        return pedidoRepository.listarCards(pageable);
    }

    public Pedido get(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        if (pedido != null) {
            return pedido;
        } else {
            throw new RuntimeException("Pedido nao encontrado");
        }
    }

    public Boolean delete(Long id) {
        try {
            Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
            pedido.setStatus(StatusPedido.CANCLED);
            pedido.setAtivo(false);
            pedidoRepository.save(pedido);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean deleteAll() {
        try {
            pedidoRepository.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Pedido findByUuid(String uuid) {
        return pedidoRepository.findById_Pedido(uuid).orElseThrow(RecursoNaoEncontradoException::new);
    }

    public PedidoResponse update(Long pedidoId, PedidoUpdateDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        if (dto.getStatus() != null)
            pedido.setStatus(StatusPedido.valueOf(dto.getStatus()));

        if (dto.getItens() != null) {
            atualizarItens(pedido, dto);
            pedido.recalcularTotal();
        }

        if (dto.getFrete() != null) {
            pedido.atualizarFrete(dto.getFrete());
        }

        Pagamentos pagamento = pedido.getPagamentos();

        if (pagamento != null) {
            pagamento.atualizarComNovoTotal(pedido.getValor_total());
        }

        var response = new PedidoResponse(pedidoRepository.save(pedido));
        System.out.println(response.itens());
        return response;
    }

    private void atualizarItens(Pedido pedido, PedidoUpdateDTO dto) {
        List<PedidoItem> novosItens = dto.getItens().stream().map(i -> {
            Produto produto = produtoService.get(i.produto_id());
            Variante variante = produto.getVariantes().stream()
                    .filter(v -> v.getId().equals(i.variante_id()))
                    .findFirst()
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Variante não encontrada"));

            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setVariante(variante);
            item.setPersonalizacao(i.personalizacao());
            item.setQuantidade(i.quantidade());
            item.setValor(variante.getValor() * i.quantidade());
            return item;
        }).toList();

        pedido.getItens().clear();
        pedido.getItens().addAll(novosItens);
    }

    public List<PedidoResponse> getPedidoCliente(Long id) {
        return pedidoRepository.findByClienteId(id).stream().map(PedidoResponse::new).toList();
    }

    public PedidoAbertoResponse getPedidoClienteAberto(Long id) {
        var pedido = pedidoRepository.pedidosEmAberto(id);
        if (pedido.isEmpty()) {
            List<Pedido> p = new ArrayList<>();
            return new PedidoAbertoResponse(p, clienteService.findById(id));
        }
        return new PedidoAbertoResponse(pedido, pedido.get(0).getCliente());
    }

    public PedidoResponse setStatusPedido(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(RecursoNaoEncontradoException::new);
        pedido.setStatus(StatusPedido.valueOf(status));
        return new PedidoResponse(pedidoRepository.save(pedido));
    }

    public StatusPedido getStatusPedido(Long id) {
        return pedidoRepository.getStatusPedido(id);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void limparPedidos() {
        System.out.println(pedidoRepository.clearPedidos());
    }

    @Scheduled(cron = "0 30 8 * * *")
    public void buscarPedidoComLayoutPendente() {
        List<Pedido> p = pedidoRepository.buscarPedidosComLayoutPendente();
        buscarPedidoPendentePublisher.publisherBuscarPedidoPendente(p);
    }

    public RelatorioMensalDTO relatorioMensal() {
        return pedidoRepository.relatorioMensal().get(0);
    }
}
