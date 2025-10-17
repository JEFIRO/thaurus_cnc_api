package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.model.Produto;

import java.util.Map;

public record ItemResponse(
        Long id,
        PedidoResponse pedido,
        Produto produto,
        Map<String, Object> personalizacao,
        Double valor,
        Double frete) {
    public ItemResponse(PedidoItem item) {
        this(item.getId(), new PedidoResponse(item.getPedido()), item.getProduto(), item.getPersonalizacao(), item.getValor(), item.getFrete());

    }
}
