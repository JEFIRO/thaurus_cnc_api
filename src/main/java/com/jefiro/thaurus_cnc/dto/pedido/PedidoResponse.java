package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.dto.Frete;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.model.Cliente;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        String id_Pedido,
        Cliente cliente,
        List<PedidoItemResponse> itens,
        Double valor_customizacao,
        Double valor_total,
        StatusPedido status,
        Frete frete,
        Boolean ativo,
        PagamentoResponse pagamento,
        LocalDateTime data_pedido) {
    public PedidoResponse(Pedido pedido) {
        this(pedido.getId(), pedido.getId_Pedido()
                , pedido.getCliente(), pedido.getItens().stream()
                        .map(PedidoItemResponse::new).toList()
                , pedido.getValor_customizacao()
                , pedido.getValor_total(), pedido.getStatus()
                , pedido.getFrete()
                ,pedido.isAtivo()
                ,new PagamentoResponse(pedido.getPagamentos()), pedido.getData_pedido());
    }
}
