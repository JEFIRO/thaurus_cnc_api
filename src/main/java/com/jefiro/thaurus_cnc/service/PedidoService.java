package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    ClienteService clienteService;

    public Pedido newPedido(NewPedido pedido) {
        if (pedido.clienteDTO() == null || pedido.pedidoDTO() == null) {
            throw new IllegalArgumentException();
        }
        Cliente cliente = null;
        cliente = clienteService.findByTelefone(pedido.clienteDTO().telefone());
        cliente.setEndereco(pedido.clienteDTO().endereco());

        if (cliente == null) {
            cliente = clienteService.novo(pedido.clienteDTO());
        }


        Pedido pedidoEntity = new Pedido(pedido.pedidoDTO());
        pedidoEntity.setCliente(cliente);

        return pedidoRepository.save(pedidoEntity);
    }

    public Pedido newPedido(Long idCliente, PedidoDTO pedido) {
        if (idCliente == null || pedido == null) {
            throw new IllegalArgumentException("idCliente e pedido devem ser informados");
        }
        Cliente cliente = clienteService.findById(idCliente);
        Pedido pedidoEntity = new Pedido(pedido);
        pedidoEntity.setCliente(cliente);
        return pedidoRepository.save(pedidoEntity);
    }
}
