package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;

import java.util.Optional;
import java.util.UUID;

public interface AssinaturaGateway {
    String criarCustom(Assinatura assinatura);

    String criarAssinatura(String customId);

    void cancelar(String idAssinatura);
}
