package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Medida;

import java.util.Map;

public record ProdutoDTO(
        String nome,
        String descricao,
        Double preco,
        String imagem,
        Medida medida_produto,
        Medida medida_embalegem,
        Map<String, Object> personalizacao
) {
}
