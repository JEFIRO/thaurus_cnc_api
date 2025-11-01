package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.dto.Frete;
import lombok.Data;

import java.util.List;

@Data
public class PedidoUpdateDTO {
    private List<PedidoItemDTO> itens;
    private Double valor_total;
    private String status;
    private Frete frete;
}

