package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.AssinaturaNaoEncontradaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssinaturaUseCase {

    private final AssinaturaGateway gateway;
    private final UsuarioUseCase usuarioUseCase;

    public void criar(Assinatura assinatura) {

        Usuario usuario = usuarioUseCase.consultarPorId(assinatura.getIdUsuario());

        if(usuario.getCustomerId() == null) {
            String idCustom = gateway.criarCustom(assinatura);
            usuario.setCustomerId(idCustom);
            usuarioUseCase.salvar(usuario);
        }


        gateway.criarAssinatura(usuario.getCustomerId());
    }

    public void cancelar(UUID idAssinatura) {
        this.consultarPorId(idAssinatura);
        gateway.deletar(idAssinatura);
    }

    private void consultarPorId(UUID idAssinatura) {
        Optional<Assinatura> assinatura = gateway.consultarPorId(idAssinatura);

        if(assinatura.isEmpty()) {
            throw new AssinaturaNaoEncontradaException();
        }
    }
}
