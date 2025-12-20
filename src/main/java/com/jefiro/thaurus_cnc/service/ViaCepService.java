package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.model.Endereco;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepService {
    private final WebClient webClient;

    public ViaCepService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://viacep.com.br/ws/").build();
    }

    public Endereco gerarEndereco(String cep) {
        return webClient.get()
                .uri(cep + "/json")
                .exchangeToMono(response -> {

                    HttpStatusCode status = response.statusCode();

                    if (status == HttpStatus.BAD_GATEWAY) {
                        return Mono.just(new Endereco());
                    }

                    if (status.is2xxSuccessful()) {
                        return response.bodyToMono(Endereco.class);
                    }

                    return Mono.error(
                            new RuntimeException("Erro HTTP: " + status.value())
                    );
                })
                .block();
    }


}
