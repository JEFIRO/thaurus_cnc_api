package com.jefiro.thaurus_cnc.dto.cliente;

import com.jefiro.thaurus_cnc.model.Endereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record NewCliente(
        @NotNull
        String remoteJid,
        @NotNull
        String nome,
        String telefone,
        @Email
        String email,
        String cpf,
        Endereco endereco) {}
