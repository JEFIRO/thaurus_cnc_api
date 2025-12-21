package com.jefiro.thaurus_cnc.dto.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record PagamentoResquest(
        @PositiveOrZero
        double valorPago,
        String observacao,
        @NotBlank
        String metodoPagamento
) {
}
