package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.ProdutoDTO;
import com.jefiro.thaurus_cnc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<?> novo(@RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok().body(service.novo(dto));
    }

    @PostMapping("list")
    public ResponseEntity<?> novo(@RequestBody List<ProdutoDTO> dto) {
        return ResponseEntity.ok().body(service.novo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @DeleteMapping
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok(service.delete());
    }
}
