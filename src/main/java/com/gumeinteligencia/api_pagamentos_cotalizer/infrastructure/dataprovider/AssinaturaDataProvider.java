package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.AssinaturaRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.AssinaturaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class AssinaturaDataProvider implements AssinaturaGateway {

    private final AssinaturaRepository repository;

    private final WebClient webClient;

    @Value("${stripe.acess.token}")
    private final String SECRET_KEY;

    @Value("${stripe.assinatura.id}")
    private final String ASSINATURA_ID;

    public AssinaturaDataProvider(
            AssinaturaRepository repository,
            WebClient webClient,
            @Value("${stripe.acess.token}") String SECRET_KEY,
            @Value("${stripe.assinatura.id}") String ASSINATURA_ID
    ) {
        this.repository = repository;
        this.webClient = webClient;
        this.SECRET_KEY = SECRET_KEY;
        this.ASSINATURA_ID = ASSINATURA_ID;
    }

    @Override
    public String criarCustom(Assinatura assinatura) {
        Map response = webClient.post()
                .uri("/v1/customers")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                .bodyValue("email=" + assinatura.getCustomerEmail() + "&payment_method=" + assinatura.getPaymentMethodId() + "&invoice_settings[default_payment_method]=" + assinatura.getPaymentMethodId())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return response.get("id").toString();
    }

    @Override
    public String criarAssinatura(String customerId) {
        Map responseRequest = webClient.post()
                .uri("/v1/subscriptions")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                .bodyValue("customer=" + customerId + "&items[0][price]=" + ASSINATURA_ID)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            log.error("Erro HTTP Stripe: {}", body);
                            return Mono.error(new RuntimeException("Erro HTTP Stripe: " + body));
                        })
                )
                .bodyToMono(Map.class)
                .block();

        return responseRequest.get("id").toString();
    }

    @Override
    public void cancelar(String idAssinatura) {
        webClient.delete()
                .uri("/v1/subscriptions/" + idAssinatura)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            log.error("Erro HTTP Stripe: {}", body);
                            return Mono.error(new RuntimeException("Erro HTTP Stripe: " + body));
                        })
                )
                .bodyToMono(Map.class)
                .block();
    }
}
