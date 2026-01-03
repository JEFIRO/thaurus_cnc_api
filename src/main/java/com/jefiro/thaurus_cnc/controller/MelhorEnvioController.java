package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.CalculaFrete;
import com.jefiro.thaurus_cnc.dto.melhorenvio.MelhorEnvioTokenResponse;
import com.jefiro.thaurus_cnc.service.MelhorEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController()
@RequestMapping()
public class MelhorEnvioController {
    @Autowired
    private MelhorEnvioService seuService;

    @GetMapping("/melhorenvio/callback")
    public Mono<MelhorEnvioTokenResponse> handleMelhorEnvioCallback(@RequestParam("code") String code) {
        return seuService.obterAccessToken(code);
    }
    @GetMapping("/calcularFrete/{id}")
    public ResponseEntity<?> calcularFrete(@PathVariable Long id) {
        return ResponseEntity.ok(seuService.calcularFreteComMap(id));
    }
    @PostMapping("/calcularFrete/{cep}")
    public ResponseEntity<?> calcularFrete(@PathVariable String cep, @RequestBody List<CalculaFrete> calculaFrete) {
        return ResponseEntity.ok(seuService.calcularFrete(calculaFrete,cep));
    }
}
