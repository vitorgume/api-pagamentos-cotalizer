package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PagamentoResponseDto {
    private Long id;
    private LocalDate date_created;
    private LocalDate date_approved;
    private LocalDate date_last_updated;
}
