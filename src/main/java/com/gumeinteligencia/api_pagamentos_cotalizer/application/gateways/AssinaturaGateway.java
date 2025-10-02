package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;

public interface AssinaturaGateway {
    String criarCustom(Assinatura assinatura);

    String criarAssinatura(String customId, String idPlano);

    void cancelar(String idAssinatura);
}
