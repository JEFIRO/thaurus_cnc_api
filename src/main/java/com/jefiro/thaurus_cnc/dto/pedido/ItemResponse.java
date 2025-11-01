package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.model.Variante;

import java.util.Map;

public record ItemResponse(
        Long id,
        String nome_produto,
        String id_produto,
        Variante variante,
        Map<String, Object> personalizacao,
        Double valor,
        Integer quantidade) {
    public ItemResponse(PedidoItem item) {
        this(
                item.getId_item(),
                item.getProduto().getNome(),
                item.getProduto().getId().toString(),
                item.getVariante(),
                item.getPersonalizacao(),
                item.getValor(),
                item.getQuantidade()
        );
    }
}

