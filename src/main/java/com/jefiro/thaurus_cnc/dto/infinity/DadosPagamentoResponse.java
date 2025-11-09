package com.jefiro.thaurus_cnc.dto.infinity;

import com.jefiro.thaurus_cnc.model.Infinitepay.DadosPagamento;
import com.jefiro.thaurus_cnc.model.Infinitepay.Pagamentos;
import jakarta.persistence.ManyToOne;
import lombok.ToString;

import java.time.LocalDateTime;

public record DadosPagamentoResponse(
        Long id,
        String id_pagamento,
        String transaction_nsu,
        String reciboCliente,
        String formaPagamento,
        LocalDateTime dataPagamento,
        Double valorPago,
        String recibo
) {
    public DadosPagamentoResponse(DadosPagamento dadosPagamento) {
        this(dadosPagamento.getId()
                , dadosPagamento.getPagamento().getId_pagamento()
                , dadosPagamento.getTransaction_nsu()
                , dadosPagamento.getReciboCliente()
                , dadosPagamento.getFormaPagamento()
                , dadosPagamento.getDataPagamento()
                , dadosPagamento.getValorPago()
                , dadosPagamento.getRecibo());
    }
}
