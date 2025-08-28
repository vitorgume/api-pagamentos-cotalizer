package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
public class AssinaturaDataProvider implements AssinaturaGateway {

    private final WebClient webClient;

    @Value("${stripe.secret.key}")
    private final String SECRET_KEY;

    @Value("${stripe.assinatura.id}")
    private final String ASSINATURA_ID;

    private final String MENSAGEM_ERRO_CRIAR_CUSTOMER = "Erro ao criar customer.";
    private final String MENSAGEM_ERRO_CRIAR_ASSINATURA = "Erro ao criar uma assinatura.";
    private final String MENSAGEM_ERRO_CANCELAR_ASSINATURA = "Erro ao cancelar assinatura.";

    public AssinaturaDataProvider(
            WebClient webClient,
            @Value("${stripe.secret.key}") String SECRET_KEY,
            @Value("${stripe.assinatura.id}") String ASSINATURA_ID
    ) {
        this.webClient = webClient;
        this.SECRET_KEY = SECRET_KEY;
        this.ASSINATURA_ID = ASSINATURA_ID;
    }

    @Override
    public String criarCustom(Assinatura assinatura) {
        Map response;

        try {
            response = webClient.post()
                    .uri("/v1/customers")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                    .bodyValue("email=" + assinatura.getCustomerEmail() + "&payment_method=" + assinatura.getPaymentMethodId() + "&invoice_settings[default_payment_method]=" + assinatura.getPaymentMethodId())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CRIAR_CUSTOMER, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CRIAR_CUSTOMER, ex.getCause());
        }

        return response.get("id").toString();
    }

    @Override
    public String criarAssinatura(String customerId) {
        Map responseRequest;

        try {
            responseRequest = webClient.post()
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
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CRIAR_ASSINATURA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CRIAR_ASSINATURA, ex.getCause());
        }

        return responseRequest.get("id").toString();
    }

    @Override
    public void cancelar(String idAssinatura) {
        try {
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
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CANCELAR_ASSINATURA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CANCELAR_ASSINATURA, ex.getCause());
        }
    }
}
