package com.jefiro.thaurus_cnc.dto.infinity;

import com.jefiro.thaurus_cnc.dto.pedido.PedidoResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class InfinitypayDTO {
    @Value("${infinityPay.handle}")
    private String handle;
    @Value("${infinityPay.redirect.url}")
    private String redirect_url;
    @Value("${infinityPay.webhook.url}")
    private String webhook_url;

    private String order_nsu;
    private List<InfinitypayItens> itens;

    public InfinitypayDTO(List<InfinitypayItens> itens, String order) {
        this.itens = itens;
        this.order_nsu = order;
    }
}
