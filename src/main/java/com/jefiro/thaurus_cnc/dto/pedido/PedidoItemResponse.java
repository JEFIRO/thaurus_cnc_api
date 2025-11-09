package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.model.PedidoItem;

import java.util.Map;

public record PedidoItemResponse(
        Long id_item,
        Long id_produto,
        String nome_Produto,
        VarianteResponse variante,
        Map<String, Object> personalizacao,
        Integer quantidade,
        Double valor) {
    public PedidoItemResponse(PedidoItem item) {
        this(item.getId_item(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                new VarianteResponse(item.getVariante()),
                item.getPersonalizacao(),
                item.getQuantidade(),
                item.getValor());
    }
}
