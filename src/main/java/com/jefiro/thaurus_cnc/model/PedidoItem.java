package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.PedidoItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Variante variante;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;

    private Double valor;
    private Double frete;

    public PedidoItem(PedidoItemDTO dto) {
        this.personalizacao = dto.personalizacao();
        this.valor = dto.valor();
        this.frete = dto.frete();
    }

    public PedidoItem(PedidoDTO dto, Produto produto) {
        this.personalizacao = dto.personalizacao();
        this.valor = dto.valor();
        this.pedido = new Pedido(dto);
        this.produto = produto;
    }
}
