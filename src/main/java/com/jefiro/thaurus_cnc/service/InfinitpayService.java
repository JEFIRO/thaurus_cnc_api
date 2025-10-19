package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.InfinitypayDTO;
import com.jefiro.thaurus_cnc.dto.PedidoResponse;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InfinitpayService {
    @Autowired
    private PedidoService pedidoService;

    private final WebClient webClient;

    public InfinitpayService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.infinitepay.io/invoices/public/checkout/links").build();
    }

    public Mono<String> gerarLink(Long pedidoId) {
        PedidoResponse pedido = pedidoService.get(pedidoId);

        if (pedido.status().equals(StatusPedido.LAYOUT_PENDING)){
            throw new IllegalArgumentException("Seu Layout ainda não foi aprovado.");
        }

        InfinitypayDTO infinitypayDTO = new InfinitypayDTO(pedido);

        System.out.println(infinitypayDTO);
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(infinitypayDTO)
                .retrieve()
                .bodyToMono(String.class);
    }

}
