package com.jefiro.thaurus_cnc.controller;


import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public ResponseEntity<?> novo(@RequestBody ClienteDTO dto) {
        return ResponseEntity.ok().body(clienteService.novo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<?> findByTelefone(@PathVariable String telefone) {
        return ResponseEntity.ok().body(clienteService.findByTelefone(telefone));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok().body(clienteService.findByCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(clienteService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok().body(clienteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteService.delete(id));
    }

}
