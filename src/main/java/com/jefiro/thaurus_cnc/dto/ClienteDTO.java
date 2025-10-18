package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;

public record ClienteDTO(
        String nome,
        String telefone,
        String email,
        String cpf,
        Endereco endereco) {
    public ClienteDTO(Cliente cliente) {
        this(cliente.getNome(), cliente.getTelefone(), cliente.getEmail(), cliente.getCpf(), cliente.getEndereco());
    }
}
