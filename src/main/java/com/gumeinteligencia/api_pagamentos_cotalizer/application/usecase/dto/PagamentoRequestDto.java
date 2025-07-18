package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PagamentoRequestDto {

    private String description;
    private Integer installments;
    private Payer payer;
    private String token;
    private BigDecimal transaction_amount;

    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Payer {
        private String email;
    }
}
