package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper.AssinaturaMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.AssinaturaRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.AssinaturaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssinaturaDataProvider implements AssinaturaGateway {

    private final String MENSAGEM_ERRO_SALVAR_ASSINATURA = "Erro ao salvar assinatura.";
    private final String MENSAGEM_ERRO_DELETAR_ASSINATURA = "Erro ao deletar assinatura.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar assinatura pelo seu id.";

    private final AssinaturaRepository repository;

    @Override
    public Assinatura salvar(Assinatura assinatura) {
        AssinaturaEntity assinaturaEntity = AssinaturaMapper.paraEntity(assinatura);

        try {
            assinaturaEntity = repository.save(assinaturaEntity);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_ASSINATURA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_ASSINATURA, ex.getCause());
        }

        return AssinaturaMapper.paraDomain(assinaturaEntity);
    }

    @Override
    public void deletar(UUID idAssinatura) {
        try {
            repository.deleteById(idAssinatura);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR_ASSINATURA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_ASSINATURA, ex.getCause());
        }
    }

    @Override
    public Optional<Assinatura> consultarPorId(UUID idAssinatura) {
        Optional<AssinaturaEntity> assinaturaEntity;

        try {
            assinaturaEntity = repository.findById(idAssinatura);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }

        return assinaturaEntity.map(AssinaturaMapper::paraDomain);
    }
}
