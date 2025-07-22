package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.AutoRecurring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssinaturaRequestDto {
    private String preapproval_plan_id;
    private String reason;
    private String payer_eamail;
    private String card_token_id;
    private AutoRecurring auto_recurring;
    private String back_url;
}
