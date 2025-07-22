package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.AutoRecurring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanoRequestDto {
    private String reason;
    private AutoRecurring auto_recurring;
    private String back_url;
}
