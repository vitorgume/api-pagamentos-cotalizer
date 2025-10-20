package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.PlanoNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PlanoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoPlano;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanoUseCase {

    private final PlanoGateway gateway;

    public Plano consultarPorId(String idPlano) {
        Optional<Plano> planoExistente = gateway.consultarPorId(idPlano);

        if(planoExistente.isEmpty()) {
            throw new PlanoNaoEncontradoException();
        }

        return planoExistente.get();
    }

    public Plano consultarPlanoPadrao() {
        Optional<Plano> plano = gateway.consultarPlanoPeloTipo(TipoPlano.PADRAO);

        if(plano.isEmpty()) {
            throw new PlanoNaoEncontradoException();
        }

        return plano.get();
    }
}
