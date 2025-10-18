package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.model.Produto;

import java.util.Map;

public record ItemResponse(
        Long id,
        Long produtoId,
        Long variante,
        Map<String, Object> personalizacao,
        Double valor
) {
    public ItemResponse(PedidoItem item) {
        this(
                item.getId(),
                item.getProduto().getId(),
                item.getVariante().getId(),
                item.getPersonalizacao(),
                item.getValor()
        );
    }
}

