package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.Frete;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor

@Table
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_Pedido;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens;
    private Double valor;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    @Embedded
    private Frete frete;
    private LocalDateTime data_pedido;
    private LocalDateTime data_finalizacao;
    private boolean ativo;

    public Pedido() {
        this.id_Pedido = UUID.randomUUID().toString();
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }

    public Pedido(PedidoDTO dto) {
        this.id_Pedido = UUID.randomUUID().toString();
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }
}
