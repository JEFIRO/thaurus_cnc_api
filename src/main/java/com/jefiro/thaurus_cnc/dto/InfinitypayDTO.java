package com.jefiro.thaurus_cnc.dto;

import java.util.List;

public record InfinitypayDTO(
        String handle,
        String redirect_url,
        String webhook_url,
        String order_nsu,
        List<InfinitypayItens> items
) {
    public InfinitypayDTO(PedidoResponse pedido) {
        this("thauruscnc",
                "https://thauruscnc-n8n.eaou4y.easypanel.host",
                "https://thauruscnc-n8n.eaou4y.easypanel.host/webhook/963315d4-d08c-4fce-b9b1-4b94b597c343",
                pedido.id().toString(),
                pedido.itens().stream().map(i -> new InfinitypayItens(1, i.valor(),
                        i.produto().getNome())).toList()
        );
    }
}
