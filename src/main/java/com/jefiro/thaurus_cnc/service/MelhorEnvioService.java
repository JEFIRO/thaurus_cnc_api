package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.dto.MelhorEnvioTokenDTO;
import com.jefiro.thaurus_cnc.dto.MelhorEnvioTokenResponse;
import com.jefiro.thaurus_cnc.model.MelhorEnvioToken;
import com.jefiro.thaurus_cnc.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

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

                    token.setAccessToken(tokenResponse.access_token());
                    token.setRefreshToken(tokenResponse.refresh_token());

                    Instant expiresAt = Instant.now().plusSeconds(tokenResponse.expires_in());
                    token.setExpiresAt(expiresAt);

                    return Mono.fromRunnable(() -> tokenRepository.save(token))
                            .thenReturn(tokenResponse);
                });
    }
}