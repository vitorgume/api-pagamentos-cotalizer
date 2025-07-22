package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Assinatura {
    private String paymentMethodId;
    private String customerEmail;
    private String idUsuario;
}
