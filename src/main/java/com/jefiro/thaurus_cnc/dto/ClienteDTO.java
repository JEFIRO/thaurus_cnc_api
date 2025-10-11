package com.jefiro.thaurus_cnc.dto;

import com.jefiro.thaurus_cnc.model.Endereco;

public record ClienteDTO(
        Long id,
        String nome,
        String telefone,
        String email,
        String cpf,
        Endereco endereco) {
}
