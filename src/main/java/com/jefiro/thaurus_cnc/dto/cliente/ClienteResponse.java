package com.jefiro.thaurus_cnc.dto.cliente;

import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Endereco;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long id,
        String remoteJid,
        String nome,
        String telefone,
        String email,
        String cpf,
        Endereco endereco,
        LocalDateTime data_cadastro,
        boolean ativo
) {
    public ClienteResponse(Cliente cliente) {
        this(cliente.getId(),
                cliente.getRemoteJid(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getEndereco(),
                cliente.getData_cadastro(),
                cliente.isAtivo());
    }
}
