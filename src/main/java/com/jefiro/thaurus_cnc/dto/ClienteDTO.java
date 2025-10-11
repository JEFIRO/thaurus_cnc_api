package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Endereco;

public record ClienteDTO(
        String nome,
        String telefone,
        String email,
        String cpf,
        Endereco endereco) {
}
