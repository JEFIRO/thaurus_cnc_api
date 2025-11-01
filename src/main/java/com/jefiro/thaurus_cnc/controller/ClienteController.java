package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.dto.cliente.ClienteResponse;
import com.jefiro.thaurus_cnc.dto.cliente.ClienteUpdate;
import com.jefiro.thaurus_cnc.dto.cliente.NewCliente;
import com.jefiro.thaurus_cnc.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> novo(@RequestBody @Valid NewCliente cliente) {
        return ResponseEntity.ok().body(clienteService.novo(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteService.findById(id));
    }

    @GetMapping("/remoteJid/{jid}")
    public ResponseEntity<?> findByRemoteJid(@PathVariable String jid) {
        return ResponseEntity.ok().body(clienteService.findByRemoteJid(jid));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Page<ClienteResponse>> findByCpf(
            @PathVariable String cpf,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(clienteService.findByCpf(cpf, page, size, sortBy, direction));
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Page<ClienteResponse>> findByTelefone(
            @PathVariable String telefone,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(clienteService.findByTelefone(telefone, page, size, sortBy, direction));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<ClienteResponse>> findByNome(
            @PathVariable String nome,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(clienteService.findByNome(nome, page, size, sortBy, direction));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(clienteService.findAll(page, size, sortBy, direction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteUpdate cliente) {
        return ResponseEntity.ok(clienteService.update(id, cliente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.delete(id));
    }
}
