package com.gumeinteligencia.api_pagamentos_cotalizer.application.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Pagamento;

public class PagamentoMapper {
    public static Pagamento paraDomain(PagamentoResponseDto response) {
        return Pagamento.builder()
                .dataCriacaoPagamento(response.getDate_created())
                .dataAprovacao(response.getDate_approved())
                .dataUltimaAlteracao(response.getDate_last_updated())
                .build();
    }
}
