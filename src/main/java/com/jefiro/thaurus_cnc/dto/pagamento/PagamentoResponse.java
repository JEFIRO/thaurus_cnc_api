package com.jefiro.thaurus_cnc.dto.pagamento;

import com.jefiro.thaurus_cnc.dto.infinity.DadosPagamentoResponse;
import com.jefiro.thaurus_cnc.model.Infinitepay.Pagamentos;
import com.jefiro.thaurus_cnc.model.Infinitepay.StatusPagamento;

import java.time.LocalDateTime;
import java.util.List;

public record PagamentoResponse(
        Long id,
        String id_pagamento,
        Long pedido,
        String pedido_uuid,
        Double valorRestante,
        Double valorTotal,
        Double valorPago,
        List<DadosPagamentoResponse> dadosPagamentos,
        StatusPagamento status,
        String observacao,
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
                pagamentos.getDadosPagamentos() != null
                        ? pagamentos.getDadosPagamentos().stream()
                        .map(DadosPagamentoResponse::new)
                        .toList()
                        : List.of(),
                pagamentos.getStatus(),
                pagamentos.getObservacao(),
                pagamentos.getData_cadastro()
        );
    }
}
