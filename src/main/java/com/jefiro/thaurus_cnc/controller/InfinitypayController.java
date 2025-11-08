package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.model.Infinitepay.InfinitepayWebhook;
import com.jefiro.thaurus_cnc.model.Infinitepay.Pagamentos;
import com.jefiro.thaurus_cnc.service.infinitepay.InfinitpayService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class InfinitypayController {
    @Autowired
    private InfinitpayService service;

    @GetMapping("link/{id}/{value}")
    public ResponseEntity<?> enviarDados(@PathVariable @Valid @NotNull Long id, @PathVariable Double value) {
        return ResponseEntity.ok(service.gerarLink(id, value));
    }

    @GetMapping("link/{id}")
    public ResponseEntity<?> enviarDados(@PathVariable @Valid @NotNull Long id) {
        return ResponseEntity.ok(service.gerarLink(id, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable @Valid @NotNull Long id) {
        return ResponseEntity.ok(new PagamentoResponse(service.get(id)));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Pagamentos> receberWebhook(@RequestBody InfinitepayWebhook payload) {
        return ResponseEntity.ok(service.webhook(payload));
    }
}
