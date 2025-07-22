package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AssinaturaResponseDto {
    private String id;
    private Integer application_id;
    private Integer collector_id;
    private String reason;
}
