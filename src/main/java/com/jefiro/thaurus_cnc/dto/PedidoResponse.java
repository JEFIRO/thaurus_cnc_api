package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.dto.cliente.ClienteDTO;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;
public record PedidoResponse(
        Long id,
        ClienteDTO cliente,
        List<ItemResponse> itens,
        Double valor,
        StatusPedido status,
        Frete frete,
        LocalDateTime data_pedido,
        LocalDateTime data_finalizacao,
        boolean ativo
) {
    public PedidoResponse(Pedido pedido) {
        this(
                pedido.getId(),
                new ClienteDTO(pedido.getCliente()),
                pedido.getItens().stream().map(ItemResponse::new).toList(),
                pedido.getValor(),
                pedido.getStatus(),
                pedido.getFrete(),
                pedido.getData_pedido(),
                pedido.getData_finalizacao(),
                pedido.isAtivo()
        );
    }
}
