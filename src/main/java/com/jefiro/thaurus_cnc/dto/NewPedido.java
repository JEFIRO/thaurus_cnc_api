package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.dto.cliente.NewCliente;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoDTO;
import jakarta.validation.Valid;

import java.util.List;

public record NewPedido(
        @Valid
        NewCliente clienteDTO,
        List<PedidoDTO> pedidoDTO,
        Frete frete
) {
}
