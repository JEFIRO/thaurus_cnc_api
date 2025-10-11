package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> pedidoRepository(@RequestBody NewPedido pedido) {
        return ResponseEntity.ok().body(pedidoService.newPedido(pedido));
    }

}
