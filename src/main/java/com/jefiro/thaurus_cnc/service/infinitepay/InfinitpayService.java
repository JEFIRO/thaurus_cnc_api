package com.jefiro.thaurus_cnc.service.infinitepay;

import com.jefiro.thaurus_cnc.dto.infinity.InfinitypayDTO;
import com.jefiro.thaurus_cnc.dto.infinity.InfinitypayItens;
import com.jefiro.thaurus_cnc.dto.pedido.PedidoResponse;
import com.jefiro.thaurus_cnc.infra.exception.StatusInvalidoException;
import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.model.StatusPedido;
import com.jefiro.thaurus_cnc.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfinitpayService {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private InfinitypayConfig infinitypayConfig;

    private final WebClient webClient;

    public InfinitpayService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.infinitepay.io/invoices/public/checkout/links").build();
    }

    private Pedido manipulaPedido(Long id, Double valorAdicinal) {
        Pedido pedido = pedidoService.get(id);

        if (valorAdicinal != null) {
            pedido.setValor_customizacao(valorAdicinal);
            pedido.setValor_total(pedido.getValor_total() + valorAdicinal);
            pedido = pedidoService.upSimples(pedido);
        }
        return pedido;
    }

    public Mono<String> gerarLink(Long pedidoId, Double valorAdicinal) {
        Pedido pedido = manipulaPedido(pedidoId, valorAdicinal);

        if (pedido.getStatus().equals(StatusPedido.LAYOUT_PENDING)) {
            throw new StatusInvalidoException("Seu Layout ainda não foi aprovado.");
        }
        List<InfinitypayItens> items = new ArrayList<>(
                pedido.getItens().stream()
                        .map(item -> new InfinitypayItens(
                                item.getQuantidade(),
                                item.getVariante().getValor(),
                                item.getProduto().getNome()
                        ))
                        .toList()
        );

        items.add(new InfinitypayItens(1, pedido.getFrete().valor_frete(), "Frete: " + pedido.getFrete().metodo()));

        if (pedido.getValor_customizacao() != null) {
            items.add(new InfinitypayItens(1, pedido.getValor_customizacao(), "Valores referente a costumiações adicional "));
        }

        InfinitypayDTO infinitypayDTO = new InfinitypayDTO(
                items,
                pedido.getId_Pedido(),
                infinitypayConfig.getHandle(),
                infinitypayConfig.getRedirectUrl(),
                infinitypayConfig.getWebhookUrl()
        );

        System.out.println(infinitypayDTO);
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(infinitypayDTO)
                .retrieve()
                .bodyToMono(String.class);
    }

}
