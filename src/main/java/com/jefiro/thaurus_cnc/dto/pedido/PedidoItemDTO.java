package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.model.Variante;

import java.util.Map;

public record PedidoItemDTO(
        Long id_item,
        Produto produto,
        Variante variante,
        Map<String, Object> personalizacao,
        Integer quantidade){}
