package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.MercadoPagoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.AssinaturaRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.AssinaturaResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PlanoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PlanoResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    public PlanoResponseDto criarPlano(PlanoRequestDto planoRequestDto) {
        return webClient.post()
                .uri("https://api.mercadopago.com/preapproval_plan")
                .header("Authorization", "Bearer " + ACESS_TOKEN)
                .bodyValue(planoRequestDto)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            log.error("Erro HTTP Mercado Pago: {}", body);
                            return Mono.error(new RuntimeException("Erro HTTP: " + body));
                        })
                )
                .bodyToMono(PlanoResponseDto.class)
                .block();
    }

    @Override
    public AssinaturaResponseDto criarAssinatura(AssinaturaRequestDto assinaturaRequestDto) {
        return webClient.post()
                .uri("https://api.mercadopago.com/preapproval")
                .header("Authorization", "Bearer " + ACESS_TOKEN)
                .bodyValue(assinaturaRequestDto)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class).flatMap(body -> {
                            log.error("Erro HTTP Mercado Pago: {}", body);
                            return Mono.error(new RuntimeException("Erro HTTP: " + body));
                        })
                ).bodyToMono(AssinaturaResponseDto.class)
                .block();
    }
}
