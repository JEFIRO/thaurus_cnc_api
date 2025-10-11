package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Personalizacao;

public record PedidoDTO(
        Cliente cliente,
        String produto_id,
        String produto_nome,
        Personalizacao personalizacao,
        Double valor,
        Double frete) {
}
