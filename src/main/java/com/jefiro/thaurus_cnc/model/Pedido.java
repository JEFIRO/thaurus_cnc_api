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
    @ManyToOne
    private Produto produto;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String,Object> personalizacao;
    private Double valor;
    private Double frete;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;
    private LocalDateTime data_pedido;
    private LocalDateTime data_finalizacao;
    private boolean ativo;


    public Pedido(PedidoDTO dto) {
        this.cliente = dto.cliente();
        this.personalizacao = dto.personalizacao();
        this.valor = dto.valor();
        this.frete = dto.frete();
        this.status = StatusPedido.LAYOUT_PENDING;
        this.data_pedido = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }
}
