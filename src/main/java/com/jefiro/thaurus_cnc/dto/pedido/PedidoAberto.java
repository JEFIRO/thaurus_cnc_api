package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.dto.Frete;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoAberto(
        Long id,
        String id_Pedido,
        List<PedidoItemResponse> itens,
        Double valor_customizacao,
        Double valor_total,
        StatusPedido status,
        Frete frete,
        LocalDateTime data_pedido) {
    public PedidoAberto(Pedido pedido) {
        this(pedido.getId(), pedido.getId_Pedido()
                , pedido.getItens().stream()
                        .map(PedidoItemResponse::new).toList()
                , pedido.getValor_customizacao()
                , pedido.getValor_total(), pedido.getStatus()
                , pedido.getFrete(), pedido.getData_pedido());
    }
}
