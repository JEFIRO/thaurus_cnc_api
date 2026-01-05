package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResquest;
import com.jefiro.thaurus_cnc.dto.pedido.NewPedido;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoResponse;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoUpdateDTO;
import com.jefiro.thaurus_cnc.model.pagamento.Pagamentos;
import com.jefiro.thaurus_cnc.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> newPedido(@RequestBody @Valid NewPedido pedido) {
        return ResponseEntity.ok().body(pedidoService.newPedido(pedido));
    }

    @GetMapping()
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok().body(pedidoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new PedidoResponse(pedidoService.get(id)));
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PedidoUpdateDTO dto) {
        return ResponseEntity.ok().body(pedidoService.update(id, dto));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> findByPedidoCliente(@PathVariable Long id) {
        return ResponseEntity.ok().body(pedidoService.getPedidoCliente(id));
    }

    @GetMapping("/cliente/pedidoaberto/{id}")
    public ResponseEntity<?> findByPedidoClienteAberto(@PathVariable Long id) {
        return ResponseEntity.ok().body(pedidoService.getPedidoClienteAberto(id));
    }

    @GetMapping("/status/{id}/{stats}")
    public ResponseEntity<?> setStatus(@PathVariable Long id, @PathVariable String stats) {
        return ResponseEntity.ok().body(pedidoService.setStatusPedido(id, stats));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok().body(pedidoService.getStatusPedido(id));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<?> relatorio() {
        return ResponseEntity.ok().body(pedidoService.relatorioMensal());
    }
}

