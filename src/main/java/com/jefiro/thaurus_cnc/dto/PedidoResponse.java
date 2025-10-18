package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.PedidoItem;
import com.jefiro.thaurus_cnc.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;
public record PedidoResponse(
        Long id,
        List<ItemResponse> itens,
        Double valor,
        Double frete,
        StatusPedido status,
        LocalDateTime data_pedido,
        LocalDateTime data_finalizacao,
        boolean ativo
) {
    public PedidoResponse(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getItens().stream().map(ItemResponse::new).toList(),
                pedido.getValor(),
                pedido.getFrete(),
                pedido.getStatus(),
                pedido.getData_pedido(),
                pedido.getData_finalizacao(),
                pedido.isAtivo()
        );
    }
}
