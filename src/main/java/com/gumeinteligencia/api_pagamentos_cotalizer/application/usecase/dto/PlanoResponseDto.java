package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class PlanoResponseDto {
    private String id;
    private String application_id;
    private String collector_id;
    private String reason;
}
