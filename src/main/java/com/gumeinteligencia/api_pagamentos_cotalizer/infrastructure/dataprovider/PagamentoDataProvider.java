package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PagamentoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class PagamentoDataProvider implements PagamentoGateway {

    private final WebClient webClient;

    @Value("${mp.acess.token}")
    private final String ACESS_TOKEN;


    @Override
    public PagamentoResponseDto enviarPagamento(PagamentoRequestDto pagamentoRequestDto) {



        return null;
    }
}
