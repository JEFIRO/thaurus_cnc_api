package com.jefiro.thaurus_cnc.dto.pedido;

import com.jefiro.thaurus_cnc.dto.cliente.ClienteCard;
import com.jefiro.thaurus_cnc.dto.pagamento.PagamentoResponse;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;

import java.time.LocalDateTime;


public record PedidoCard(
        Long id,
        String id_Pedido,
        ClienteCard cliente,
        PedidoItemResponse item,
        Integer quantidade_itens,
        Double valor_total,
        Double valor_customizacao,
        StatusPedido status,
        PagamentoResponse pagamentos,
        LocalDateTime data_pedido

) {
    public PedidoCard(Pedido pedido) {
        this(pedido.getId()
                ,pedido.getId_Pedido()
                ,new ClienteCard(pedido.getCliente())
                ,new PedidoItemResponse(pedido.getItens().get(0))
                ,pedido.getItens().size()
                ,pedido.getValor_total()
                ,pedido.getValor_customizacao()
                ,pedido.getStatus()
                ,new PagamentoResponse(pedido.getPagamentos())
                ,pedido.getData_pedido());
    }
}
