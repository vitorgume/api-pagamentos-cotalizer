package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.MercadoPagoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.*;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.AutoRecurring;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MercadoPagoUseCase {

    private final MercadoPagoGateway gateway;

    public PlanoResponseDto criarPlano() {
        PlanoRequestDto planoRequestDto = PlanoRequestDto.builder()
                .reason("Plano Plus")
                .auto_recurring(
                        AutoRecurring.builder()
                                .frequency(1)
                                .frequency_type("months")
                                .repetitions(12)
                                .billing_day(5)
                                .billing_day_proportional(false)
                                .transaction_amount(BigDecimal.valueOf(39.90))
                                .currency_id("BRL")
                                .build()
                )
                .back_url("https://www.gumeinteligencia.com.br/")
                .build();

        return gateway.criarPlano(planoRequestDto);
    }

    public AssinaturaResponseDto criarAssinatura(String planoId, String cardTokenId, String email) {
        AssinaturaRequestDto assinaturaRequestDto = AssinaturaRequestDto.builder()
                .preapproval_plan_id(planoId)
                .reason("Plano Plus")
                .payer_eamail(email)
                .card_token_id(cardTokenId)
                .auto_recurring(
                        AutoRecurring.builder()
                                .frequency(1)
                                .frequency_type("months")
                                .transaction_amount(BigDecimal.valueOf(39.90))
                                .currency_id("BRL")
                                .build()
                )
                .back_url("https://www.gumeinteligencia.com.br/")
                .build();

        return gateway.criarAssinatura(assinaturaRequestDto);
    }
}
