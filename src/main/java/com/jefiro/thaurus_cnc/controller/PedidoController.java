package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.PedidoDTO;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> pedidoRepository(@RequestBody NewPedido pedido) {
        return ResponseEntity.ok().body(pedidoService.newPedido(pedido));
    }

    @PostMapping("/{id_cliente}")
    public ResponseEntity<Pedido> pedidoRepository(@PathVariable Long id_cliente, @RequestBody PedidoDTO pedido) {
        return ResponseEntity.ok().body(pedidoService.newPedido(id_cliente, pedido));
    }

    @GetMapping()
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(pedidoService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.delete(id));
    }

    @DeleteMapping
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok(pedidoService.deleteAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PedidoDTO dto) {
        return ResponseEntity.ok().body(pedidoService.update(id, dto));
    }

}
