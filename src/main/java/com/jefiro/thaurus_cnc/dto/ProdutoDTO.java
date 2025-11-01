package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Variante;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record ProdutoDTO(
        Long id,
        @NotNull
        String nome,
        @NotNull
        String descricao,
        @NotNull
        String imagem,
        @NotNull
        List<Variante> variantes,
        @NotNull
        Map<String, Object> personalizacao
) {
}
