package com.jefiro.thaurus_cnc.Notificador.publisher;

import com.jefiro.thaurus_cnc.Notificador.event.NewPedidoEvent;
import com.jefiro.thaurus_cnc.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PedidoPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishPedidoCriado(Pedido pedido) {
        publisher.publishEvent(new NewPedidoEvent(pedido));
    }
}
