package com.jefiro.thaurus_cnc.dto;

import jakarta.validation.Valid;

import java.util.List;

public record NewPedido(
        @Valid
        ClienteDTO clienteDTO,
        List<PedidoDTO> pedidoDTO,
        Frete frete
) {
}
