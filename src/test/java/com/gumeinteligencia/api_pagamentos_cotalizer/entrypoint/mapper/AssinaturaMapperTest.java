package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AssinaturaMapperTest {

    private AssinaturaDto assinaturaDto;

    @BeforeEach
    void setUp() {
        assinaturaDto = AssinaturaDto.builder()
                .paymentMethodId("method-test")
                .customerEmail("emailteste@gmail.com")
                .idUsuario(UUID.randomUUID().toString())
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