package com.jefiro.thaurus_cnc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Variante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "altura", column = @Column(name = "altura_produto")),
            @AttributeOverride(name = "largura", column = @Column(name = "largura_produto")),
            @AttributeOverride(name = "profundidade", column = @Column(name = "profundidade_produto")),
            @AttributeOverride(name = "peso", column = @Column(name = "peso_produto"))
    })
    @ToString.Exclude
    private Medida medida_produto;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "altura", column = @Column(name = "altura_embalagem")),
            @AttributeOverride(name = "largura", column = @Column(name = "largura_embalagem")),
            @AttributeOverride(name = "profundidade", column = @Column(name = "profundidade_embalagem")),
            @AttributeOverride(name = "peso", column = @Column(name = "peso_embalagem"))
    })
    @ToString.Exclude
    private Medida medida_embalagem;


    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private Produto produto;
}
