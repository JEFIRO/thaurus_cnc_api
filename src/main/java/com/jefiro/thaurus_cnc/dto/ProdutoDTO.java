package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Variante;

import java.util.List;
import java.util.Map;

public record ProdutoDTO(
        String nome,
        String descricao,
        String imagem,
        List<Variante> variantes,
        Map<String, Object> personalizacao
) {
}
