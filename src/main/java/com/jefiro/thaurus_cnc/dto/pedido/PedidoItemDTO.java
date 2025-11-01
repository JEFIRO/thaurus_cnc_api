package com.jefiro.thaurus_cnc.dto.pedido;

import java.util.Map;

public record PedidoItemDTO(
        Long produto_id,
        Long variante_id,
        Map<String, Object> personalizacao,
        Integer quantidade) {
}
