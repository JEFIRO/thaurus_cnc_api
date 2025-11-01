package com.jefiro.thaurus_cnc.model;

import lombok.Getter;

@Getter
public enum StatusPedido {
    LAYOUT_PENDING("Layout pendente"),
    PENDING_PAYMENT("Aguardando pagamento"),
    IN_PRODUCTION("Em produção"),
    PREPARING_FOR_DELIVERY("Preparando para entrega"),
    ON_THE_WAY("A caminho"),
    CANCLED("Cancelado"),
    DELIVERED("Entregue");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

}
