package com.jefiro.thaurus_cnc.dto.infinity;

import com.jefiro.thaurus_cnc.dto.PedidoResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class InfinitypayDTO {
    @Value("${infinityPay.handle}")
    private String handle;
    @Value("${infinityPay.redirect.url}")
    private String redirect_url;
    @Value("${infinityPay.webhook.url}")
    private String webhook_url;

    private String order_nsu;
    private List<InfinitypayItens> items;

    public InfinitypayDTO(PedidoResponse pedido) {
        this.order_nsu = pedido.id().toString();
        pedido.itens().forEach(i -> items.add(new InfinitypayItens(1, i.valor(), i.nome_produto())));
        items.add(new InfinitypayItens(1, pedido.frete().valor_frete(), pedido.frete().metodo()));
    }
}
