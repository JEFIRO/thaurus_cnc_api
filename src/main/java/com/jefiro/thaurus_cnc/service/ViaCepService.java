package com.jefiro.thaurus_cnc.service;

import com.jefiro.thaurus_cnc.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ViaCepService {
    private final WebClient webClient;

    public ViaCepService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://viacep.com.br/ws/").build();
    }

    public Endereco gerarEndereco(String cep) {

        return webClient.get()
                .uri(cep + "/json")
                .retrieve()
                .bodyToMono(Endereco.class).block();

    }

}
