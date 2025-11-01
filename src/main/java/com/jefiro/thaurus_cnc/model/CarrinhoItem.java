package com.jefiro.thaurus_cnc.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Map;

@Data
@Entity
public class CarrinhoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Carrinho carrinho;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Variante variante;

    private Integer quantidade;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;

    public Double getSubtotal() {
        return variante.getValor() * quantidade;
    }
}
