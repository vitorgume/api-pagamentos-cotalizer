package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Identification;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;

public class AssinaturaMapper {

    public static Assinatura paraDomain(AssinaturaDto dto) {
        return Assinatura.builder()
                .paymentMethodId(dto.getPaymentMethodId())
                .customerEmail(dto.getCustomerEmail())
                .idUsuario(dto.getIdUsuario())
                .build();
    }

    public static AssinaturaDto paraDto(Assinatura domain) {
        return AssinaturaDto.builder()
                .paymentMethodId(domain.getPaymentMethodId())
                .customerEmail(domain.getCustomerEmail())
                .idUsuario(domain.getIdUsuario())
                .build();
    }
}
