package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.dto.pedido.VarianteResponse;
import com.jefiro.thaurus_cnc.model.MapToJsonConverter;
import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.model.Variante;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Map;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        String imagem,
        Boolean ativo,
        List<VarianteResponse> variantes,
        Map<String, Object> personalizacao) {
    public ProdutoResponse(Produto produto) {
        this(produto.getId()
                , produto.getNome()
                , produto.getDescricao()
                , produto.getImagem()
                , produto.getAtivo()
                , produto.getVariantes().stream().map(VarianteResponse::new).toList()
                , produto.getPersonalizacao());
    }
}
