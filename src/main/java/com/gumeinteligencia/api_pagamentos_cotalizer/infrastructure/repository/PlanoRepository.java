package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoPlano;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoRepository extends MongoRepository<PlanoEntity, String> {
    Optional<PlanoEntity> findByTipoPlano(TipoPlano tipoPlano);
}
