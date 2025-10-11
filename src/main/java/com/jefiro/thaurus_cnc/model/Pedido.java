package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Table
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cliente cliente;
    private String produto_id;
    private String produto_nome;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String,Object> personalizacao;
    private Double valor;
    private Double frete;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    LocalDateTime data_pedido;
    LocalDateTime data_finalizacao;

    public Pedido(PedidoDTO dto) {
        this.cliente = dto.cliente();
        this.produto_id = dto.produto_id();
        this.produto_nome = dto.produto_nome();
        this.personalizacao = dto.personalizacao();
        this.valor = dto.valor();
        this.frete = dto.frete();
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
    }
}
