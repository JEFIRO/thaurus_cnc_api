package com.jefiro.thaurus_cnc.dto;

import java.util.Map;

public record PedidoItemDTO(
        Long cliente,
        Long pedido,
        Long produto,
        Map<String, Object> personalizacao,
        Double valor,
        Double frete
) {
}
