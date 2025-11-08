package com.jefiro.thaurus_cnc.model.Infinitepay;

import com.jefiro.thaurus_cnc.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor

@Table
@Entity
public class Pagamentos {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String id_pagamento;

    @OneToOne(mappedBy = "pagamentos")
    @ToString.Exclude
    private Pedido pedido;

    private Double valorRestante;
    private Double valorTotal;
    @OneToMany(mappedBy = "pagamento")
    @ToString.Exclude
    private List<DadosPagamento> dadosPagamentos;
    private StatusPagamento status;
    private String observacao;
    private LocalDateTime data_cadastro;

    public Pagamentos() {
        this.id_pagamento = UUID.randomUUID().toString();
        this.data_cadastro = LocalDateTime.now();
        this.status = StatusPagamento.PENDING_PAYMENT;
    }
    public Pagamentos(Double valorTotal) {
        this.valorTotal = valorTotal;
        this.valorRestante = valorTotal;
        this.id_pagamento = UUID.randomUUID().toString();
        this.data_cadastro = LocalDateTime.now();
        this.status = StatusPagamento.PENDING_PAYMENT;
    }
}
