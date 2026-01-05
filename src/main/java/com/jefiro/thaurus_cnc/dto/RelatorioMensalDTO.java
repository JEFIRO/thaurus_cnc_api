package com.jefiro.thaurus_cnc.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RelatorioMensalDTO {
    LocalDateTime getMesRef();

    String getMesNome();

    Long getQuantidadePedido();

    BigDecimal getValorTotalPedidos();

    BigDecimal getMediaValorPedido();

    BigDecimal getMaiorValorPedido();

    BigDecimal getMenorValorPedido();

    Long getQuantidadeClientes();

    Long getQuantidadeItens();
}
