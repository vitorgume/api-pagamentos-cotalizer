package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class IdentificationDto {
    private String type;
    private String number;
}
