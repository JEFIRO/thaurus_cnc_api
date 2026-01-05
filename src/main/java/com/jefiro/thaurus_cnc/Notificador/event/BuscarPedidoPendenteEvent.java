package com.jefiro.thaurus_cnc.Notificador.event;

import com.jefiro.thaurus_cnc.model.Pedido;
import lombok.Getter;

import java.util.List;

@Getter
public class BuscarPedidoPendenteEvent {
    List<Pedido> pedidos;

    public BuscarPedidoPendenteEvent(List<Pedido> pedidos) {
        super();
        this.pedidos = pedidos;
    }

}
