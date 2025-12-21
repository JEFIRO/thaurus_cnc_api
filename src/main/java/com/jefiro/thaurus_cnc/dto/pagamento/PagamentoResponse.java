package com.jefiro.thaurus_cnc.dto.pagamento;

import com.jefiro.thaurus_cnc.model.pagamento.MetodoPagamento;
import com.jefiro.thaurus_cnc.model.pagamento.Pagamentos;
import com.jefiro.thaurus_cnc.model.pagamento.StatusPagamento;

import java.time.LocalDateTime;

public record PagamentoResponse(
        Long id,
        String id_pagamento,
        Long pedido,
        String pedido_uuid,
        Double valorRestante,
        Double valorTotal,
        Double valorPago,
        StatusPagamento status,
        String observacao,
        MetodoPagamento metodoPagamento,
        LocalDateTime data_cadastro
) {
    public PagamentoResponse(Pagamentos pagamentos) {
        this(
                pagamentos.getId(),
                pagamentos.getId_pagamento(),
                pagamentos.getPedido() != null ? pagamentos.getPedido().getId() : null,
                pagamentos.getPedido() != null ? pagamentos.getPedido().getId_Pedido() : null,
                pagamentos.getValorRestante(),
                pagamentos.getValorTotal(),
                pagamentos.getValorPago(),
                pagamentos.getStatus(),
                pagamentos.getObservacao(),
                pagamentos.getMetodoPagamento(),
                pagamentos.getData_cadastro()
        );
    }
}
