package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.AssinaturaEntity;

public class AssinaturaMapper {

    public static AssinaturaEntity paraEntity(Assinatura domain) {
        return AssinaturaEntity.builder()
                .id(domain.getId())
                .valor(domain.getValor())
                .dataCriacao(domain.getDataCriacao())
                .ultimaRenovacao(domain.getUltimaRenovacao())
                .emailUsuario(domain.getEmailUsuario())
                .build();
    }

    public static Assinatura paraDomain(AssinaturaEntity entity) {
        return Assinatura.builder()
                .id(entity.getId())
                .valor(entity.getValor())
                .dataCriacao(entity.getDataCriacao())
                .ultimaRenovacao(entity.getUltimaRenovacao())
                .emailUsuario(entity.getEmailUsuario())
                .build();
    }
}
