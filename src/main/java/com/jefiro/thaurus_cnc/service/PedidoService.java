package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.PedidoResponse;
import com.jefiro.thaurus_cnc.model.*;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import com.jefiro.thaurus_cnc.repository.VarianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(pedido);
        if (pedido.clienteDTO() == null || pedido.pedidoDTO() == null) {
            throw new IllegalArgumentException();
        }
        Cliente cliente = null;
        cliente = clienteService.findByTelefone(pedido.clienteDTO().telefone());


        if (cliente == null) {
            cliente = clienteService.novo(pedido.clienteDTO());
        }

        cliente.setEndereco(pedido.clienteDTO().endereco());

        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setCliente(cliente);

        List<PedidoItem> itens = new ArrayList<>();


        for (PedidoDTO dto : pedido.pedidoDTO()) {
            Produto produto = produtoService.get(dto.produto_id());


            PedidoItem item = new PedidoItem(dto, produto);
            Variante variante = varianteRepository.findById(dto.variante()).orElseThrow(() -> new RuntimeException("Variante nao encontrada"));
            item.setPedido(pedidoEntity);
            item.setVariante(variante);
            itens.add(item);
        }

        pedidoEntity.setItens(itens);
        double total = itens.stream().mapToDouble(PedidoItem::getValor).sum();
        pedidoEntity.setValor(total);

        pedidoEntity.setFrete(pedido.frete());
        pedidoEntity = pedidoRepository.save(pedidoEntity);

        return new PedidoResponse(pedidoEntity);
    }

    public PedidoResponse newPedido(Long idCliente, List<PedidoDTO> pedidoDTOS) {
        if (idCliente == null || pedidoDTOS == null) {
            throw new IllegalArgumentException("idCliente e pedido devem ser informados");
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

        double total = itens.stream().mapToDouble(PedidoItem::getValor).sum();
        pedido.setValor(total);

        pedido.setFrete(pedidoDTOS.get(0).frete());
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

        // Atualiza cliente se necessário
        if (!dtoList.isEmpty() && dtoList.get(0).cliente() != null) {
            pedido.setCliente(dtoList.get(0).cliente());
        }

        // Atualiza os itens
        List<PedidoItem> itensAtualizados = dtoList.stream().map(dto -> {
            Produto produto = produtoService.get(dto.produto_id());
            PedidoItem item = new PedidoItem();
            item.setProduto(produto);
            item.setValor(dto.valor());
            item.setPersonalizacao(dto.personalizacao());
            item.setPedido(pedido);
            return item;
        }).toList();

        pedido.getItens().clear();
        pedido.getItens().addAll(itensAtualizados);


        pedido.setValor(itensAtualizados.stream().mapToDouble(PedidoItem::getValor).sum());

        return pedidoRepository.save(pedido);
    }

}
