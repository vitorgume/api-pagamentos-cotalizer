package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.PlanoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssinaturaDto {
    private String paymentMethodId;
    private String customerEmail;
    private String idUsuario;
    private PlanoUsuario plano;
}