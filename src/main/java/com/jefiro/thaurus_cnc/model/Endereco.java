package com.jefiro.thaurus_cnc.model;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Column(nullable = true)
    private String cep;
    @Column(nullable = true)
    private String logradouro;
    @Column(nullable = true)
    private String complemento;
    @Column(nullable = true)
    private String numero;
    @Column(nullable = true)
    private String bairro;
    @JsonProperty("localidade")
    @Column(nullable = true)
    private String cidade;
    @Column(nullable = true)
    private String uf;
    @Column(nullable = true)
    private String ddd;
    private boolean erro;
}
