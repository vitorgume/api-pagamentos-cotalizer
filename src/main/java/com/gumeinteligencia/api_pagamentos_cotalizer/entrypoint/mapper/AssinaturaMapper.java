package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;

public class AssinaturaMapper {

    public static Assinatura paraDomain(AssinaturaDto dto) {
        return Assinatura.builder()
                .id(dto.getId())
                .valor(dto.getValor())
                .dataCriacao(dto.getDataCriacao())
                .ultimaRenovacao(dto.getUltimaRenovacao())
                .emailUsuario(dto.getEmailUsuario())
                .build();
    }

    public static AssinaturaDto paraDto(Assinatura domain) {
        return AssinaturaDto.builder()
                .id(domain.getId())
                .valor(domain.getValor())
                .dataCriacao(domain.getDataCriacao())
                .ultimaRenovacao(domain.getUltimaRenovacao())
                .emailUsuario(domain.getEmailUsuario())
                .build();
    }
}
