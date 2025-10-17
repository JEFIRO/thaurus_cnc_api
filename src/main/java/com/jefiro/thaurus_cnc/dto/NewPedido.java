package com.jefiro.thaurus_cnc.dto;

import java.util.List;

public record NewPedido(
        ClienteDTO clienteDTO,
        List<PedidoDTO> pedidoDTO,
        Double frete
) {
}
