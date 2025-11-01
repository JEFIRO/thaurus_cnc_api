package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.pedido.PedidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class PedidoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_item;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Variante variante;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;
    private Integer quantidade;
    private Double valor;

    public PedidoItem(PedidoDTO dto, Produto produto,Variante variante) {
        this.personalizacao = dto.personalizacao();
        this.valor = produto.getVariantes().stream().filter(p -> p.getId().equals(dto.variante())).findFirst().orElseThrow().getValor() * dto.quantidade();
        this.quantidade = dto.quantidade();
        this.produto = produto;
        this.variante = variante;
    }
}
