package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.*;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;

public interface MercadoPagoGateway {
    PlanoResponseDto criarPlano(PlanoRequestDto planoRequestDto);

    AssinaturaResponseDto criarAssinatura(AssinaturaRequestDto assinaturaRequestDto);
}
