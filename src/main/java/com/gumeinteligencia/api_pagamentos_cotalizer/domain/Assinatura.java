package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Assinatura {
    private UUID id;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaRenovacao;
    private String emailUsuario;
    private Pagamento dadosPagamento;
    private String cardholderName;
    private String idUsuario;
    private Identification identification;
}
