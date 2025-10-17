package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.PedidoItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor


@Table
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens;
    private Double valor;
    private Double frete;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    private LocalDateTime data_pedido;
    private LocalDateTime data_finalizacao;
    private boolean ativo;

    public Pedido() {
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }

    public Pedido(PedidoItem dto) {
        this.itens = List.of(dto);
        this.valor = dto.getValor();
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }
    public Pedido(PedidoDTO dto){
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }

}
