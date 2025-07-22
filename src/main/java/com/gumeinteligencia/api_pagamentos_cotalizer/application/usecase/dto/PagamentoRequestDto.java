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
    private String card_id;
    private BigDecimal transaction_amount;

    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Payer {
        private String email;
        private Identification identification;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Identification {
        private String type;
        private String number;
    }
}
