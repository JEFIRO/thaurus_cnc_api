package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.service.InfinitpayService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class InfinitypayController {
    @Autowired
    private InfinitpayService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> enviarDados(@PathVariable @Valid @NotNull Long id) {
        return ResponseEntity.ok(service.gerarLink(id));
    }

}
