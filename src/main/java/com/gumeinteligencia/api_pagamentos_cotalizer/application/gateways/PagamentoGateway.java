package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;

public interface PagamentoGateway {
    PagamentoResponseDto enviarPagamento(PagamentoRequestDto pagamentoRequestDto);
}

