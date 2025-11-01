package com.jefiro.thaurus_cnc.dto.cliente;

import com.jefiro.thaurus_cnc.model.Endereco;
import jakarta.validation.constraints.Email;


public record ClienteUpdate(
        String remoteJid,
        String nome,
        String telefone,
        @Email
        String email,
        String cpf,
        Endereco endereco) {
}
