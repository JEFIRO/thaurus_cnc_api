package com.jefiro.thaurus_cnc.Notificador.publisher;

import com.jefiro.thaurus_cnc.Notificador.event.BuscarPedidoPendenteEvent;
import com.jefiro.thaurus_cnc.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BuscarPedidoPendentePublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void publisherBuscarPedidoPendente(List<Pedido> pedido) {
        publisher.publishEvent(new BuscarPedidoPendenteEvent(pedido));
    }

}
