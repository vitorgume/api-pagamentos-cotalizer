package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PlanoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper.PlanoMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.PlanoRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlanoDataProvider implements PlanoGateway {

    private final PlanoRepository repository;
    private static final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar plano pelo seu id.";
    private static final String MENSAGEM_ERRO_CONSULTAR_PLANO_PADRAO = "Erro ao consultar plano padrão verdadeiro.";

    @Override
    public Optional<Plano> consultarPorId(String idPlano) {
        Optional<PlanoEntity> planoEntity;

        try {
            planoEntity = repository.findById(idPlano);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }

        return planoEntity.map(PlanoMapper::paraDomain);
    }

    @Override
    public Optional<Plano> consultarPlanoPadrao() {
        Optional<PlanoEntity> planoEntity;

        try {
            planoEntity = repository.findByPadraoTrue();
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_PLANO_PADRAO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_PLANO_PADRAO, ex.getCause());
        }

        return planoEntity.map(PlanoMapper::paraDomain);
    }
}
