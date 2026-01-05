package com.jefiro.thaurus_cnc.Notificador.event;

import com.jefiro.thaurus_cnc.model.Pedido;
import lombok.Getter;

@Getter
public class NewPedidoEvent {
    private final Pedido pedido;

    public NewPedidoEvent(Pedido pedido) {
        super();
        this.pedido = pedido;
    }
}
