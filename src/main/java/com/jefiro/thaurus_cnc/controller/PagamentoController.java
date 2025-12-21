package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResquest;
import com.jefiro.thaurus_cnc.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService service;

    @PostMapping("/{id}")
    public ResponseEntity<PagamentoResponse> pagarPedido(@PathVariable @Valid @NotNull Long id, @RequestBody @Valid PagamentoResquest pagamentoResquest) {
        return ResponseEntity.ok().body(service.pagarPedido(id, pagamentoResquest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> buscarPagamentos(@PathVariable @Valid @NotNull Long id) {
        return ResponseEntity.ok().body(service.buscarPagamento(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponse> atualizarPedido(@PathVariable @Valid @NotNull Long id, @RequestBody @Valid PagamentoResquest pagamentoResquest) {
        return ResponseEntity.ok().body(service.atualizarPedido(id, pagamentoResquest));
    }

}
