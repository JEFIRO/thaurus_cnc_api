package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.MelhorEnvioTokenResponse;
import com.jefiro.thaurus_cnc.service.MelhorEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/")
public class MelhorEnvioController {
    @Autowired
    private MelhorEnvioService seuService;
    @GetMapping("/melhorenvio/callback")
    public Mono<MelhorEnvioTokenResponse> handleMelhorEnvioCallback(@RequestParam("code") String code) {
        return seuService.obterAccessToken(code);
    }
}
