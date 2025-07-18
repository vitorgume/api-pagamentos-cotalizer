package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository;

import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.AssinaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssinaturaRepository extends JpaRepository<AssinaturaEntity, UUID> {
}
