package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private String imagem;
    private Boolean ativo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private List<Variante> variantes;


    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> personalizacao;

    public Produto(ProdutoDTO dto) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.imagem = dto.imagem();
        this.variantes = dto.variantes();
        this.personalizacao = dto.personalizacao();
        this.ativo = true;
    }


}
