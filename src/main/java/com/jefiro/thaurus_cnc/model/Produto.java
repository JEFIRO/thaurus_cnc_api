package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String imagem;
    @Embedded
    private Medida medida_produto;
    @Embedded
    private Medida medida_embalegem;
    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;

    public Produto(ProdutoDTO dto) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.preco = dto.preco();
        this.imagem = dto.imagem();
        this.medida_produto = dto.medida_produto();
        this.medida_embalegem = dto.medida_embalegem();
        this.personalizacao = dto.personalizacao();
    }
}
