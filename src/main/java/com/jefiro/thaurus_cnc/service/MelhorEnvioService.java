package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.CalculaFrete;
import com.jefiro.thaurus_cnc.dto.melhorenvio.MelhorEnvioToken;
import com.jefiro.thaurus_cnc.dto.melhorenvio.MelhorEnvioTokenDTO;
import com.jefiro.thaurus_cnc.dto.melhorenvio.MelhorEnvioTokenResponse;
import com.jefiro.thaurus_cnc.infra.exception.RecursoNaoEncontradoException;
import com.jefiro.thaurus_cnc.model.Produto;
import com.jefiro.thaurus_cnc.model.Variante;
import com.jefiro.thaurus_cnc.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;

@Service
public class MelhorEnvioService {

    @Value("${melhorenvio.client.id}")
    private Integer clientId;
    @Value("${melhorenvio.client.secret}")
    private String clientSecret;
    @Value("${melhorenvio.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    private final WebClient webClient;

    public MelhorEnvioService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://melhorenvio.com.br").build();
    }

    public Mono<MelhorEnvioTokenResponse> obterAccessToken(String code) {


        MelhorEnvioTokenDTO requestBody = new MelhorEnvioTokenDTO(
                "authorization_code",
                clientId,
                clientSecret,
                redirect_uri,
                code,
                null
        );

        return webClient.post()
                .uri("/oauth/token")
                .header("Accept", "application/json")
                .header("User-Agent", "Sua Aplicação (jefiroo@gmail.com)")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(MelhorEnvioTokenResponse.class)
                .flatMap(tokenResponse -> {
                    MelhorEnvioToken token = new MelhorEnvioToken();
                    token.setId(1L);
                    token.setAccessToken(tokenResponse.access_token());
                    token.setRefreshToken(tokenResponse.refresh_token());

                    Instant expiresAt = Instant.now().plusSeconds(tokenResponse.expires_in());
                    token.setExpiresAt(expiresAt);

                    return Mono.fromRunnable(() -> tokenRepository.save(token))
                            .thenReturn(tokenResponse);
                });
    }


    private Mono<MelhorEnvioTokenResponse> renovarAccessToken(String refreshToken) {

        MelhorEnvioTokenDTO requestBody = new MelhorEnvioTokenDTO(
                "refresh_token",
                clientId,
                clientSecret,
                null,
                null,
                refreshToken
        );

        return webClient.post()
                .uri("/oauth/token")
                .header("Accept", "application/json")
                .header("User-Agent", "Sua Aplicação (jefiroo@gmail.com)")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(MelhorEnvioTokenResponse.class)
                .flatMap(novoTokenResponse -> {

                    MelhorEnvioToken tokenParaSalvar = new MelhorEnvioToken();

                    tokenParaSalvar.setId(1L);

                    tokenParaSalvar.setAccessToken(novoTokenResponse.access_token());
                    tokenParaSalvar.setRefreshToken(novoTokenResponse.refresh_token());


                    Instant expiresAt = Instant.now().plusSeconds(novoTokenResponse.expires_in());
                    tokenParaSalvar.setExpiresAt(expiresAt);

                    return Mono.fromRunnable(() -> tokenRepository.save(tokenParaSalvar))
                            .thenReturn(novoTokenResponse);
                });
    }

    public Mono<String> calcularFreteComMap(Long pedidoDTO) {
        var pedido = pedidoService.get(pedidoDTO);
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("from", Map.of("postal_code", "96020360"));
        requestBody.put("to", Map.of("postal_code", pedido.getCliente().getEndereco().getCep()));

        List<Map<String, Object>> productList = new ArrayList<>();
        pedido.getItens().forEach(item -> {
            Map<String, Object> product = new HashMap<>();
            product.put("id", item.getProduto());
            product.put("width", item.getVariante().getMedida_embalagem().getLargura());
            product.put("height", item.getVariante().getMedida_embalagem().getAltura());
            product.put("length", item.getVariante().getMedida_embalagem().getProfundidade());
            product.put("weight", 0.5);
            product.put("insurance_value", item.getVariante().getValor());
            product.put("quantity", item.getQuantidade());
            productList.add(product);
        });
        requestBody.put("products", productList);

        requestBody.put("options", Map.of("receipt", false, "own_hand", false));
        requestBody.put("services", "1,2,18");

        return Mono.fromCallable(() -> tokenRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("Token não encontrado")))
                .flatMap(token -> {

                    Mono<String> accessTokenMono;

                    if (token.getExpiresAt().isBefore(Instant.now())) {
                        System.out.println("Token expirado! Renovando...");
                        accessTokenMono = renovarAccessToken(token.getRefreshToken())
                                .map(MelhorEnvioTokenResponse::access_token);
                    } else {
                        accessTokenMono = Mono.just(token.getAccessToken());
                    }


                    return accessTokenMono.flatMap(accessToken -> {
                        return webClient.post()
                                .uri("/api/v2/me/shipment/calculate")
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "Sua Aplicação (jefiroo@gmail.com)")
                                .header("Authorization", "Bearer " + accessToken) // Usa o token válido
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class);
                    });
                });
    }

    public Mono<String> calcularFrete(List<CalculaFrete> produtos, String cep) {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("from", Map.of("postal_code", "96020360"));
        requestBody.put("to", Map.of("postal_code", cep));

        List<Map<String, Object>> productList = new ArrayList<>();

        produtos.forEach(produto -> {
            Produto p = produtoService.get(produto.id_Produto());
            Variante variante = p.getVariantes().stream()
                    .filter(pr -> Objects.equals(pr.getId(), produto.id_variante()))
                    .findFirst().orElseThrow(() -> new RecursoNaoEncontradoException("Variante não encontarada"));

            Map<String, Object> product = new HashMap<>();
            product.put("id", p.getId());
            product.put("width", variante.getMedida_embalagem().getLargura());
            product.put("height", variante.getMedida_embalagem().getAltura());
            product.put("length", variante.getMedida_embalagem().getProfundidade());
            product.put("weight", variante.getMedida_embalagem().getPeso());
            product.put("insurance_value", variante.getValor());
            product.put("quantity", produto.quantidade());

            productList.add(product);

        });

        requestBody.put("products", productList);

        requestBody.put("options", Map.of("receipt", false, "own_hand", false));
        requestBody.put("services", "1,2,18");

        return Mono.fromCallable(() -> tokenRepository.findById(1L)
                        .orElseThrow(() -> new RuntimeException("Token não encontrado")))
                .flatMap(token -> {

                    Mono<String> accessTokenMono;

                    if (token.getExpiresAt().isBefore(Instant.now())) {
                        System.out.println("Token expirado! Renovando...");
                        accessTokenMono = renovarAccessToken(token.getRefreshToken())
                                .map(MelhorEnvioTokenResponse::access_token);
                    } else {
                        accessTokenMono = Mono.just(token.getAccessToken());
                    }


                    return accessTokenMono.flatMap(accessToken -> {
                        return webClient.post()
                                .uri("/api/v2/me/shipment/calculate")
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "Sua Aplicação (jefiroo@gmail.com)")
                                .header("Authorization", "Bearer " + accessToken) // Usa o token válido
                                .bodyValue(requestBody)
                                .retrieve()
                                .bodyToMono(String.class);
                    });
                });
    }
}