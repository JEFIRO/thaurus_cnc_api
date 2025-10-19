package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.model.Variante;

import java.util.Map;

public record ItemResponse(
        Long id,
        Produto produto,
        Variante variante,
        Map<String, Object> personalizacao,
        Double valor,
        Integer quantidade) {
    public ItemResponse(PedidoItem item) {
        this(
                item.getId(),
                item.getProduto(),
                item.getVariante(),
                item.getPersonalizacao(),
                item.getValor(),
                item.getQuantidade()
        );
    }
}

