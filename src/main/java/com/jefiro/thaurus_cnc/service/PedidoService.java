package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoResponse;
import com.jefiro.thaurus_cnc.infra.exception.DadosInvalidosException;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.*;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import com.jefiro.thaurus_cnc.repository.VarianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private VarianteRepository varianteRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ProdutoService produtoService;

    public PedidoResponse newPedido(NewPedido pedido) {
        if (pedido.clienteDTO() == null || pedido.pedidoDTO() == null) {
            throw new DadosInvalidosException();
        }
        Cliente cliente = null;

        try {
            cliente = clienteService.findByRemoteJid(pedido.clienteDTO().remoteJid());
            cliente = clienteService.update(cliente.getId(), pedido.clienteDTO());
        } catch (Exception e) {
            cliente = clienteService.novo(pedido.clienteDTO());
        }

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setCliente(cliente);

        List<PedidoItem> itens = new ArrayList<>();


        for (PedidoDTO dto : pedido.pedidoDTO()) {
            Produto produto = produtoService.get(dto.produto_id());

            PedidoItem item = new PedidoItem(dto, produto);
            Variante variante = varianteRepository.findById(dto.variante()).orElseThrow(() -> new RuntimeException("Variante nao encontrada"));
            item.setQuantidade(dto.quantidade());
            item.setPedido(pedidoEntity);
            item.setVariante(variante);
            itens.add(item);
        }

        pedidoEntity.setItens(itens);
        pedidoEntity.setFrete(pedido.frete());
        pedidoEntity.setValor(pedidoEntity.getItens().stream().mapToDouble(PedidoItem::getValor).sum());

        pedidoEntity = pedidoRepository.save(pedidoEntity);

        return new PedidoResponse(pedidoEntity);
    }

    public PedidoResponse newPedido(Long idCliente, List<PedidoDTO> pedidoDTOS) {
        if (idCliente == null || pedidoDTOS == null) {
            throw new DadosInvalidosException("idCliente e pedido devem ser informados");
        }

        Cliente cliente = clienteService.findById(idCliente);
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        List<PedidoItem> itens = new ArrayList<>();

        Pedido finalPedido = pedido;

        pedidoDTOS.forEach(dto -> {
            Produto produto = produtoService.get(dto.produto_id());
            PedidoItem item = new PedidoItem(dto, produto);
            item.setPedido(finalPedido);
            Variante variante = varianteRepository.findById(dto.variante()).orElseThrow(() -> new RuntimeException("Variante nao encontrada"));
            item.setVariante(variante);
            itens.add(item);
        });
        pedido.setItens(itens);

        pedido.setValor(pedido.getItens().stream().mapToDouble(PedidoItem::getValor).sum());

        pedido = pedidoRepository.save(pedido);
        return new PedidoResponse(pedido);
    }

    public List<PedidoResponse> listar() {

        return pedidoRepository.findAllAtivos().stream().map(PedidoResponse::new).toList();
    }

    public PedidoResponse get(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        if (pedido != null) {
            return new PedidoResponse(pedido);
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

    public Pedido update(Long pedidoId, List<PedidoDTO> dtoList) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!dtoList.isEmpty() && dtoList.get(0).cliente() != null) {
            pedido.setCliente(dtoList.get(0).cliente());
        }


        Map<Long, PedidoItem> itensExistentes = pedido.getItens().stream()
                .collect(Collectors.toMap(PedidoItem::getId, item -> item));

        List<PedidoItem> novosItens = new ArrayList<>();

        for (PedidoDTO dto : dtoList) {
            PedidoItem item;


            if (dto.id() != null && itensExistentes.containsKey(dto.id())) {
                item = itensExistentes.get(dto.id());
            } else {
                item = new PedidoItem();
                item.setPedido(pedido);
            }

            Produto produto = produtoService.get(dto.produto_id());
            Variante variante = produto.getVariantes().stream().filter(v -> v.getId().equals(dto.variante()))
                    .findFirst().orElseThrow(() -> new RecursoNaoEncontradoException("Variante nao encontrada"));

            item.setProduto(produto);
            item.setPersonalizacao(dto.personalizacao());
            item.setQuantidade(dto.quantidade());

            double valorItem = variante.getValor() * item.getQuantidade();

            item.setValor(valorItem);

            novosItens.add(item);
        }


        pedido.getItens().clear();
        pedido.getItens().addAll(novosItens);

        double total = novosItens.stream()
                .mapToDouble(PedidoItem::getValor)
                .sum();

        pedido.setValor(total);

        return pedidoRepository.save(pedido);
    }


    public List<PedidoResponse> getPedidoCliente(Long id) {
        return pedidoRepository.findByClienteId(id).stream().map(PedidoResponse::new).toList();
    }
}
