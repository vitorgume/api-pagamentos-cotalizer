package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.CustomRequestDto;

public interface MercadoPagoGateway {
    String criarCustomer(CustomRequestDto body);

    String salvarCartao(String customerId, String tokenCardId);
}
