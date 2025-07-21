package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository;

import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String > {
    Optional<UsuarioEntity> findById(String idUsuario);
}
