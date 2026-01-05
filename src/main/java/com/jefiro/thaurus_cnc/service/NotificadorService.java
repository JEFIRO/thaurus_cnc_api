package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.Notificador.Notificador;
import com.jefiro.thaurus_cnc.Notificador.event.BuscarPedidoPendenteEvent;
import com.jefiro.thaurus_cnc.Notificador.event.NewPedidoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificadorService {

    @Autowired
    private Notificador notificador;

    @EventListener
    public void newPedidoListener(NewPedidoEvent event) {
        System.out.println("NotificadorService");
        notificador.enviar(event.getPedido());
    }

    @EventListener
    public void BuscarPedidoPendenteListener(BuscarPedidoPendenteEvent event) {
        System.out.println("NotificadorService");
        String title = "Pedidos com layout pendente";
        String message = """
                Olá Adson, você tem %d pedidos com layout pendente há mais de 1 dia.
                """.formatted(event.getPedidos().size());

        notificador.enviarVarios(event.getPedidos(),message,title);
    }


}
