package com.jefiro.thaurus_cnc.dto.infinity;

import lombok.Data;

import java.util.List;

@Data
public class InfinitypayDTO {
    private String handle;
    private String redirect_url;
    private String webhook_url;
    private String order_nsu;
    private List<InfinitypayItens> items;

    public InfinitypayDTO(List<InfinitypayItens> itens, String order_nsu, String handle, String redirect_url, String webhook_url) {
        this.items = itens;
        this.order_nsu = order_nsu;
        this.handle = handle;
        this.redirect_url = redirect_url;
        this.webhook_url = webhook_url;
    }
}
