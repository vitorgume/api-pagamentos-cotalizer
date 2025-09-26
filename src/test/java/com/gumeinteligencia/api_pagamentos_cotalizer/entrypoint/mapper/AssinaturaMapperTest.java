package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.PlanoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AssinaturaMapperTest {

    private AssinaturaDto assinaturaDto;

    @BeforeEach
    void setUp() {
        assinaturaDto = AssinaturaDto.builder()
                .paymentMethodId("method-test")
                .customerEmail("emailteste@gmail.com")
                .idUsuario(UUID.randomUUID().toString())
                .plano(PlanoDto.builder().id("teste123").build())
                .build();
    }

    @Test
    void deveRetornarDomain() {
        Assinatura assinatura = AssinaturaMapper.paraDomain(assinaturaDto);

        Assertions.assertEquals(assinatura.getPaymentMethodId(), assinaturaDto.getPaymentMethodId());
        Assertions.assertEquals(assinatura.getCustomerEmail(), assinaturaDto.getCustomerEmail());
        Assertions.assertEquals(assinatura.getIdUsuario(), assinaturaDto.getIdUsuario());
    }
}