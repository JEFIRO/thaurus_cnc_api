package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.dto.Frete;
import com.jefiro.thaurus_cnc.dto.cliente.NewCliente;

import java.util.List;

public record NewPedido(
        NewCliente cliente,
        List<PedidoItemDTO> itens,
        Frete frete) {}
