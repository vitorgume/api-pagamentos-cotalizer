package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;

public interface AssinaturaGateway {
    String criarCustom(Assinatura assinatura);

    String criarAssinaturaPlus(String customId);

    String criarAssinaturaEnterprise(String customId);

    void cancelar(String idAssinatura);
}
