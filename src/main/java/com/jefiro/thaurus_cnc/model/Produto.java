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
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;
    private String imagem;
    private Boolean ativo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "altura", column = @Column(name = "altura_produto")),
            @AttributeOverride(name = "largura", column = @Column(name = "largura_produto")),
            @AttributeOverride(name = "profundidade", column = @Column(name = "profundidade_produto"))
    })
    private Medida medida_produto;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "altura", column = @Column(name = "altura_embalagem")),
            @AttributeOverride(name = "largura", column = @Column(name = "largura_embalagem")),
            @AttributeOverride(name = "profundidade", column = @Column(name = "profundidade_embalagem"))
    })
    private Medida medida_embalagem;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;

    public Produto(ProdutoDTO dto) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.preco = dto.preco();
        this.imagem = dto.imagem();
        this.medida_produto = dto.medida_produto();
        this.medida_embalagem = dto.medida_embalegem();
        this.personalizacao = dto.personalizacao();
        this.ativo = true;
    }
}
