package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.model.Infinitepay.DadosPagamento;
import com.jefiro.thaurus_cnc.model.Infinitepay.InfinitepayWebhook;
import com.jefiro.thaurus_cnc.model.Infinitepay.StatusPagamento;

import java.util.List;

public class Pagamentos {
    private Pedido pedido;
    private Double valorRestante;
    private Double valorTotal;
    private List<DadosPagamento> dadosPagamentos;
    private StatusPagamento status;
    private String observacao;

    public Pagamentos(Pedido pedido) {
        this.pedido = pedido;
        this.valorRestante = valorRestante;
        this.valorTotal = valorTotal;
        this.dadosPagamentos = dadosPagamentos;
        this.status = status;
        this.observacao = observacao;
    }
}
