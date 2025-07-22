package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AutoRecurring {
    private Integer frequency;
    private String frequency_type;
    private Integer repetitions;
    private Integer billing_day;
    private Boolean billing_day_proportional;
    private BigDecimal transaction_amount;
    private String currency_id;
}
