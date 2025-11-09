package com.jefiro.thaurus_cnc.dto.cliente;

import com.jefiro.thaurus_cnc.model.Cliente;

public record ClienteCard(
        Long id,
        String nome,
        String telefone,
        String email) {
    public ClienteCard(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
    }
}
