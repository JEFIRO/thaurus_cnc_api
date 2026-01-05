package com.jefiro.thaurus_cnc.Notificador;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jefiro.thaurus_cnc.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificadorPush implements Notificador {

    @Override
    public void enviar(Pedido pedido) {

        Message message = Message.builder()
                .setTopic("thaurus_all")
                .setNotification(
                        Notification.builder()
                                .setTitle("Novo Pedido")
                                .setBody("Olá Adson, Você Tem um novo pedido \n" +
                                        "Pedido #" + pedido.getId()).build()
                ).build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarVarios(List<Pedido> pedido, String message, String title) {
        Message messager = Message.builder()
                .setTopic("thaurus_all")
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(message)
                                .build()).build();

        try {
            FirebaseMessaging.getInstance().send(messager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
