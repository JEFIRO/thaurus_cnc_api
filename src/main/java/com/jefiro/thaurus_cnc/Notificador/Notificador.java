package com.jefiro.thaurus_cnc.Notificador;

import com.jefiro.thaurus_cnc.model.Pedido;

import java.util.List;

public interface Notificador {
    void enviar(Pedido pedido);
    void enviarVarios(List<Pedido> pedido,String message,String title);
}
