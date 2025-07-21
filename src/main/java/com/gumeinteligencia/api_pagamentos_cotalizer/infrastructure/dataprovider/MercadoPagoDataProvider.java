package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.MercadoPagoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.CustomRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@Slf4j
public class MercadoPagoDataProvider implements MercadoPagoGateway {

    private final WebClient webClient;

    @Value("${mp.acess.token}")
    private final String ACESS_TOKEN;

    public MercadoPagoDataProvider(
            WebClient webClient,
            @Value("${mp.acess.token}") String ACESS_TOKEN
    ) {
        this.webClient = webClient;
        this.ACESS_TOKEN = ACESS_TOKEN;
    }

    @Override
    public String criarCustomer(CustomRequestDto body) {
        Map criado = webClient.post()
                .uri("https://api.mercadopago.com/v1/payments")
                .header("Authorization", "Bearer " + ACESS_TOKEN)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return criado.get("id").toString();
    }

    @Override
    public String salvarCartao(String customerId, String tokenCardId) {
        Map<String, Object> body = Map.of("token", tokenCardId);

        Map resposta = webClient.post()
                .uri("/v1/customers/{customerId}/cards", customerId)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return resposta.get("id").toString();
    }
}
