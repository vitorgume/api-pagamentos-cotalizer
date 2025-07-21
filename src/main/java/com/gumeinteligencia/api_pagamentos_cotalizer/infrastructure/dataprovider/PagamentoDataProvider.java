package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PagamentoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

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
        return webClient
                .post()
                .uri("https://api.mercadopago.com/v1/payments")
                .header("Authorization", "Bearer " + ACESS_TOKEN)
                .bodyValue(pagamentoRequestDto)
                .retrieve()
                .bodyToMono(PagamentoResponseDto.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofSeconds(2))
                                .filter(throwable -> {
                                    log.warn("Tentando enviar pagamento novamente: {}", throwable.getMessage());
                                    return true;
                                })
                )
                .doOnError(e -> {
                    log.error(MENSAGEM_ERRO_ENVIAR_PAGAMENTO, e);
                    throw new DataProviderException(MENSAGEM_ERRO_ENVIAR_PAGAMENTO, e.getCause());
                })
                .block();
    }
}
