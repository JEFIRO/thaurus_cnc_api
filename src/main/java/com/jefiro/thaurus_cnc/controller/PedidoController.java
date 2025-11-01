package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.NewPedido;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoDTO;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoResponse;
import com.jefiro.thaurus_cnc.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> pedidoRepository(@RequestBody @Valid NewPedido pedido) {
        return ResponseEntity.ok().body(pedidoService.newPedido(pedido));
    }

    @PostMapping("/{id_cliente}")
    public ResponseEntity<PedidoResponse> criarPedido(
            @PathVariable Long id_cliente,
            @RequestBody List<PedidoDTO> pedidosDTO) {

        PedidoResponse pedidoCriado = pedidoService.newPedido(id_cliente, pedidosDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody List<PedidoDTO> dto) {
        return ResponseEntity.ok().body(pedidoService.update(id, dto));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> findByPedidoCliente(@PathVariable Long id) {
        return ResponseEntity.ok().body(pedidoService.getPedidoCliente(id));
    }


}
