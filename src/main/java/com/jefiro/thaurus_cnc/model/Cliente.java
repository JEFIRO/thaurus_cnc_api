package com.jefiro.thaurus_cnc.model;

import com.jefiro.thaurus_cnc.dto.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    @Embedded
    private Endereco endereco;
    private LocalDateTime data_cadastro;
    private LocalDateTime data_finalizacao;
    private boolean ativo;

    public Cliente(ClienteDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.endereco = dto.endereco();
        this.data_cadastro = LocalDateTime.now();
        this.data_finalizacao = LocalDateTime.now();
        this.ativo = true;
    }
}
