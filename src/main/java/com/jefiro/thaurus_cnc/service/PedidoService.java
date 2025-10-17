package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ProdutoService produtoService;

    public Pedido newPedido(NewPedido pedido) {
        if (pedido.clienteDTO() == null || pedido.pedidoDTO() == null) {
            throw new IllegalArgumentException();
        }
        Cliente cliente = null;
        cliente = clienteService.findByTelefone(pedido.clienteDTO().telefone());

        Produto produto = produtoService.get(pedido.pedidoDTO().produto_id());

        if (cliente == null) {
            cliente = clienteService.novo(pedido.clienteDTO());
        }

        cliente.setEndereco(pedido.clienteDTO().endereco());

        if (produto == null) {
            throw new IllegalArgumentException("Produto nao encontrado");
        }

        Pedido pedidoEntity = new Pedido(pedido.pedidoDTO());

        pedidoEntity.setProduto(produto);
        pedidoEntity.setCliente(cliente);

        return pedidoRepository.save(pedidoEntity);
    }

    public Pedido newPedido(Long idCliente, PedidoDTO pedido) {
        if (idCliente == null || pedido == null || pedido.produto_id() == null) {
            throw new IllegalArgumentException("idCliente e pedido devem ser informados");
        }

        Cliente cliente = clienteService.findById(idCliente);
        Produto produto = produtoService.get(pedido.produto_id());

        Pedido pedidoEntity = new Pedido(pedido);

        pedidoEntity.setProduto(produto);
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
    public Pedido update(Long id,PedidoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Pedido nao pode ser nulo");
        }
        Pedido pedido = pedidoRepository.getAtivos(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        BeanUtils.copyProperties(dto, pedido, "id");
        return pedidoRepository.save(new Pedido(dto));

    }

}
