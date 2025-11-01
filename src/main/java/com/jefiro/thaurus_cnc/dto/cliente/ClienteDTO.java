package com.jefiro.thaurus_cnc.dto.cliente;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(
        Long id,
        @NotNull
        String nome,
        @NotNull
        String remoteJid,
        String telefone,
        @Email
        String email,
        String cpf,
        Endereco endereco) {
    public ClienteDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getRemoteJid(), cliente.getTelefone(), cliente.getEmail(), cliente.getCpf(), cliente.getEndereco());
    }

}
