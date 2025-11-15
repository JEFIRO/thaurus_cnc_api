package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Pedido;

import java.util.List;

public record PedidoAbertoResponse(
        Cliente cliente,
        List<PedidoResponse> pedidoResponse) {
    public PedidoAbertoResponse(List<Pedido> pedido, Cliente cliente) {
        this(cliente, pedido.stream().map(PedidoResponse::new).toList());
    }
}


