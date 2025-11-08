package com.jefiro.thaurus_cnc.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Medida {
    private Integer altura;
    private Integer largura;
    private Integer profundidade;
    private Double peso;
}
