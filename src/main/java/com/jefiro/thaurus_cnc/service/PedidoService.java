package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.PedidoResponse;
import com.jefiro.thaurus_cnc.model.*;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
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
            item.setPedido(pedidoEntity);
            itens.add(item);
        }

        pedidoEntity.setItens(itens);
        double total = itens.stream().mapToDouble(PedidoItem::getValor).sum();
        pedidoEntity.setValor(total);

        pedidoEntity.setFrete(pedido.frete());
        pedidoEntity = pedidoRepository.save(pedidoEntity);

        return new PedidoResponse(pedidoEntity);
    }

    public Pedido newPedido(Long idCliente, PedidoDTO pedido) {
        if (idCliente == null || pedido == null || pedido.produto_id() == null) {
            throw new IllegalArgumentException("idCliente e pedido devem ser informados");
        }

        Cliente cliente = clienteService.findById(idCliente);
        Produto produto = produtoService.get(pedido.produto_id());

        Pedido pedidoEntity = new Pedido(pedido);

        //pedidoEntity.setProduto(List.of(produto));
        pedidoEntity.setCliente(cliente);

        return pedidoRepository.save(pedidoEntity);
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAllAtivos();
    }

    public Pedido get(Long id) {
        return pedidoRepository.getAtivos(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
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

    public Pedido update(Long id, PedidoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Pedido nao pode ser nulo");
        }
        Pedido pedido = pedidoRepository.getAtivos(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        BeanUtils.copyProperties(dto, pedido, "id");
        return pedidoRepository.save(new Pedido(dto));

    }

}
