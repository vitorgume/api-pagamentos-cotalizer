package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Pagamento {
    private Long id;
    private LocalDate dataCriacao;
    private LocalDate dataAprovacao;
    private LocalDate dataUltimaAlteracao;
}
