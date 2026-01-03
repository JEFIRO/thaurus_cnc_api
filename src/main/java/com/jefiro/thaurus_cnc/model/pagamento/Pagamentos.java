package com.jefiro.thaurus_cnc.model.pagamento;

import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResquest;
import com.jefiro.thaurus_cnc.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor

@Table
@Entity
public class Pagamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_pagamento;
    @OneToOne(mappedBy = "pagamentos")
    @ToString.Exclude
    private Pedido pedido;
    private Double valorPago;
    private Double valorRestante;
    private Double valorTotal;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    private String observacao;
    private LocalDateTime data_cadastro;
    private MetodoPagamento metodoPagamento;

    public Pagamentos() {
        this.id_pagamento = UUID.randomUUID().toString();
        this.data_cadastro = LocalDateTime.now();
        this.status = StatusPagamento.PENDING_PAYMENT;
    }


    public Pagamentos(PagamentoResquest pagamentoResquest, Pedido idPedido) {
        this.pedido = idPedido;
        this.id_pagamento = UUID.randomUUID().toString();
        this.data_cadastro = LocalDateTime.now();
        this.status = StatusPagamento.PAYMENT_COMPLETED;
        this.valorPago = pagamentoResquest.valorPago();
        this.observacao = pagamentoResquest.observacao();
        this.metodoPagamento = MetodoPagamento.valueOf(pagamentoResquest.metodoPagamento());
    }
}
