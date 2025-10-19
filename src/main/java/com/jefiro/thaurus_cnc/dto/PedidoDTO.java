package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Personalizacao;

import java.util.Map;

public record PedidoDTO(
        Cliente cliente,
        Long produto_id,
        Long variante,
        Map<String,Object> personalizacao,
        Double valor,
        Double frete,
        Integer quantidade) {

}
