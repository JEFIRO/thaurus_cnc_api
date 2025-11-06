package com.jefiro.thaurus_cnc.model.Infinitepay;

import java.time.LocalDateTime;

public record DadosPagamento(
        String transaction_nsu,
        String reciboCliente,
        String formaPagamento,
        LocalDateTime dataPagamento,
        Double valorPago,
        String recibo
) {
    public DadosPagamento(InfinitepayWebhook webhook) {
        this(
                webhook.getTransaction_nsu(),
                webhook.getReceipt_url(),
                webhook.getCapture_method(),
                LocalDateTime.now(),
                webhook.getAmount(),
                webhook.getReceipt_url());
    }
}
