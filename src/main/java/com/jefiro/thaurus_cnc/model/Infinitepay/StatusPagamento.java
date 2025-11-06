package com.jefiro.thaurus_cnc.model.Infinitepay;

public enum StatusPagamento {
    PENDING_PAYMENT("Aguardando pagamento"),
    PAYMENT_ENTRY("Pagamento Entrada."),
    PAYMENT_SUPPLEMENT("Restante pagamento");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
}
