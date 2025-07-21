package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Embeddable
public class Pagamento {
    private LocalDate dataCriacaoPagamento;
    private LocalDate dataAprovacao;
    private LocalDate dataUltimaAlteracao;
}
