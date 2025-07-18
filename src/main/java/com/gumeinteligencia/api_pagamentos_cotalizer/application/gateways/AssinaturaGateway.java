package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;

import java.util.Optional;
import java.util.UUID;

public interface AssinaturaGateway {
    Assinatura salvar(Assinatura assinatura);

    void deletar(UUID idAssinatura);

    Optional<Assinatura> consultarPorId(UUID idAssinatura);
}
