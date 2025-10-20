package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoPlano;

import java.util.Optional;

public interface PlanoGateway {
    Optional<Plano> consultarPorId(String idPlano);

    Optional<Plano> consultarPlanoPeloTipo(TipoPlano tipoPlano);
}
