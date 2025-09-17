package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Assinatura {
    private String paymentMethodId;
    private String customerEmail;
    private String idUsuario;
    private PlanoUsuario plano;
}
