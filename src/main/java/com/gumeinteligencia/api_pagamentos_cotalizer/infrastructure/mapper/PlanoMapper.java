package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;

public class PlanoMapper {

    public static Plano paraDomain(PlanoEntity entity) {
        return Plano.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .valor(entity.getValor())
                .limite(entity.getLimite())
                .idPlanoStripe(entity.getIdPlanoStripe())
                .padrao(entity.getPadrao())
                .sequencia(entity.getSequencia())
                .servicos(entity.getServicos())
                .build();
    }

    public static PlanoEntity paraEntity(Plano domain) {
        return PlanoEntity.builder()
                .id(domain.getId())
                .titulo(domain.getTitulo())
                .valor(domain.getValor())
                .limite(domain.getLimite())
                .idPlanoStripe(domain.getIdPlanoStripe())
                .padrao(domain.getPadrao())
                .sequencia(domain.getSequencia())
                .servicos(domain.getServicos())
                .build();
    }
}
