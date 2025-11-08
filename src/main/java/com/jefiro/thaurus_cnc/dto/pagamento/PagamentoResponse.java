package com.jefiro.thaurus_cnc.dto.pagamento;

import com.jefiro.thaurus_cnc.model.Infinitepay.DadosPagamento;
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
        List<DadosPagamento> dadosPagamentos,
        StatusPagamento status,
        String observacao,
        LocalDateTime data_cadastro
) {
    public PagamentoResponse(Pagamentos pagamentos) {
        this(pagamentos.getId(),
                pagamentos.getId_pagamento(),
                pagamentos.getPedido().getId(),
                pagamentos.getPedido().getId_Pedido(),
                pagamentos.getValorRestante(),
                pagamentos.getValorTotal(),
                pagamentos.getDadosPagamentos(),
                pagamentos.getStatus(),
                pagamentos.getObservacao(),
                pagamentos.getData_cadastro());
    }
}
