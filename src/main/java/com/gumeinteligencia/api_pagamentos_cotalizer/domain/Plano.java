package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Plano {
    private String id;
    private String titulo;
    private BigDecimal valor;
    private Integer limite;
    private String idPlanoStripe;
    private Boolean padrao;
    private Integer grau;
}
