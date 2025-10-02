package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

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
    private String idPlano;
}
