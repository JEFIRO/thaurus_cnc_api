package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.model.Pedido;
import com.jefiro.thaurus_cnc.repository.PagamentoRepository;
import com.jefiro.thaurus_cnc.repository.PedidoRepository;
import com.jefiro.thaurus_cnc.service.infinitepay.InfinitpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LembreteService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private InfinitpayService infinitpayService;

    private final WebClient webClient;

    public LembreteService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://thaurus-cnc-n8n.1o2rzn.easypanel.host/webhook/LembretePagamento/").build();
    }

    @Scheduled(fixedRate = 3600000)
    public void processarLembretes() {
        var pendentesPrimeiro = pedidoRepository.buscarPrimeiroPagamentoPendente();

        for (Pedido pedido : pendentesPrimeiro) {
            enviarLembretePrimeiroPagamento(pedido);
        }

        var pendentesSegundo = pedidoRepository.buscarSegundoPagamentoPendente();

        for (Pedido pedido : pendentesSegundo) {
            enviarLembreteSegundoPagamento(pedido);
        }
    }

    private void enviarLembretePrimeiroPagamento(Pedido pedido) {
        Map<String, Object> map = new HashMap<>();

        String link = infinitpayService.gerarLink(pedido.getId(), null).block();

        map.put("valorTotal", pedido.getValor_total());
        map.put("remoteJid", pedido.getCliente().getRemoteJid());
        map.put("pedidoNome", pedido.getItens().get(0).getProduto().getNome());
        map.put("link", link);

        enviarLembretePagamento(map, "1").subscribe();

        infinitpayService.updateLembretePagamento1(pedido.getId());
    }

    private void enviarLembreteSegundoPagamento(Pedido pedido) {
        Map<String, Object> map = new HashMap<>();

        String link = infinitpayService.gerarLink(pedido.getId(), null).block();

        map.put("valorTotal", pedido.getValor_total());
        map.put("remoteJid", pedido.getCliente().getRemoteJid());
        map.put("pedidoNome", pedido.getItens().get(0).getProduto().getNome());
        map.put("link", link);

        enviarLembretePagamento(map, "2").subscribe();

        infinitpayService.updateLembretePagamento2(pedido.getId());

    }

    private Mono<String> enviarLembretePagamento(Map<String, Object> map, String path) {
        return webClient.post().uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class);
    }



}
