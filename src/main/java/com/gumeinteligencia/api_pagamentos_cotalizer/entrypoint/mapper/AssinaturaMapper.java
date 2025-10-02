package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;

public class AssinaturaMapper {

    public static Assinatura paraDomain(AssinaturaDto dto) {
        return Assinatura.builder()
                .paymentMethodId(dto.getPaymentMethodId())
                .customerEmail(dto.getCustomerEmail())
                .idUsuario(dto.getIdUsuario())
                .idPlano(dto.getPlano().getId())
                .build();
    }
}
