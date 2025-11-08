package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.pedido.PedidoItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JoinColumn(name = "pedido_id")
    @ToString.Exclude
    private Pedido pedido;
    @ManyToOne
    private Variante variante;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;
    private Integer quantidade;
    private Double valor;

    public PedidoItem(Produto produto, Variante variante, Map<String, Object> personalizacao, Integer quantidade) {
        this.personalizacao = personalizacao;
        this.quantidade = quantidade;
        this.valor = variante.getValor() * quantidade;
        this.produto = produto;
        this.variante = variante;
    }
    public PedidoItem(Produto produto, Variante variante, Map<String, Object> personalizacao, Integer quantidade,Pedido pedido) {
        this.pedido = pedido;
        this.personalizacao = personalizacao;
        this.quantidade = quantidade;
        this.valor = variante.getValor() * quantidade;
        this.produto = produto;
        this.variante = variante;
    }
}
