package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlanoUsuario {
    GRATIS(0, "Plano Gratuito", 5),
    PLUS(1, "Plano Plus", 100),
    ENTERPRISE(2, "Plano Enterprise", 500);

    private final int codigo;
    private final String descricao;
    private final int limiteOrcamentos;
}
