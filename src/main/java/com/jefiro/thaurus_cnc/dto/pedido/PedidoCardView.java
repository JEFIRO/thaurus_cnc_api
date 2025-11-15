package com.jefiro.thaurus_cnc.dto.pedido;

import java.time.LocalDateTime;

public interface PedidoCardView {
    Long getPedidoId();

    String getIdPedido();

    Long getClienteId();

    String getClienteNome();

    String getClienteTelefone();

    String getClienteEmail();

    String getProdutoNome();

    String getImagem();

    Integer getQuantidadeItens();

    Double getValorTotal();

    String getStatus();

    LocalDateTime getDataPedido();

    Boolean getAtivo();

    String getLinkDetalhes();
}
