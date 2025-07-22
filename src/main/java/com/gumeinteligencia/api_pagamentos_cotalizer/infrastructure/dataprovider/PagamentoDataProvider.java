package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PagamentoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class PagamentoDataProvider implements PagamentoGateway {

    private final WebClient webClient;

    @Value("${mp.acess.token}")
    private final String ACESS_TOKEN;

    private final String MENSAGEM_ERRO_ENVIAR_PAGAMENTO = "Erro ao enviar pagamento.";

    public PagamentoDataProvider(
            WebClient webClient,
            @Value("${mp.acess.token}") String ACESS_TOKEN
    ) {
        this.webClient = webClient;
        this.ACESS_TOKEN = ACESS_TOKEN;
    }

    @Override
    public PagamentoResponseDto enviarPagamento(PagamentoRequestDto pagamentoRequestDto) {
        return webClient.post()
                .uri("https://api.mercadopago.com/v1/payments")
                .header("Authorization", "Bearer " + ACESS_TOKEN)
                .header("X-Idempotency-Key", UUID.randomUUID().toString())
                .bodyValue(pagamentoRequestDto)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            log.error("Erro HTTP Mercado Pago: {}", body);
                            return Mono.error(new RuntimeException("Erro HTTP: " + body));
                        })
                )
                .bodyToMono(PagamentoResponseDto.class)
                .block();

    }
}
