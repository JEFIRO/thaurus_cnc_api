package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.model.Medida;
import com.jefiro.thaurus_cnc.model.Variante;

public record VarianteResponse(
        Long id,
        Double valor,
        Medida medida_produto,
        Medida medida_embalagem) {
    public VarianteResponse(Variante variante) {
        this(variante.getId(), variante.getValor(), variante.getMedida_produto(), variante.getMedida_embalagem());
    }
}
