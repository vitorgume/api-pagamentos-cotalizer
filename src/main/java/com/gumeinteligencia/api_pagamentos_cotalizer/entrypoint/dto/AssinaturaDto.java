package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssinaturaDto {
    private UUID id;
    private BigDecimal valor;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaRenovacao;
    private String emailUsuario;
    private String tokenCardId;
    private String idUsuario;
    private String cardholderName;
    private IdentificationDto identification;
}