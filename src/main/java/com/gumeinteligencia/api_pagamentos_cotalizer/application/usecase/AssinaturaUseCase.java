package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssinaturaUseCase {

    private final AssinaturaGateway gateway;
    private final UsuarioUseCase usuarioUseCase;
    private final PlanoUseCase planoUseCase;

    public String criar(Assinatura assinatura) {
        log.info("Criando assinatura para o usuario. Assinatura: {}", assinatura);

        Usuario usuario = usuarioUseCase.consultarPorId(assinatura.getIdUsuario());

        if(usuario.getIdCustomer() == null) {
            log.info("Criando customer do usuario.");

            String idCustomer = gateway.criarCustom(assinatura);
            usuario.setIdCustomer(idCustomer);

            log.info("Customer criado com sucesso.");
        }

        Plano planoUsuario = planoUseCase.consultarPorId(assinatura.getIdPlano());

        String idAssinatura = gateway.criarAssinatura(usuario.getIdCustomer(), planoUsuario.getIdPlanoStripe());

        usuario.setPlano(planoUsuario);
        usuario.setIdAssinatura(idAssinatura);
        usuarioUseCase.salvar(usuario);

        log.info("Assinatura para o usuario criada com sucesso. Id assinatura: {}", idAssinatura);

        return idAssinatura;
    }

    public void cancelar(String idUsuario) {
        log.error("Cancelando assinatura do usuario. Id usuario: {}", idUsuario);

        Usuario usuario = usuarioUseCase.consultarPorId(idUsuario);

        gateway.cancelar(usuario.getIdAssinatura());

        Plano planoUsuario = planoUseCase.consultarPlanoPadrao();

        usuario.setPlano(planoUsuario);
        usuario.setIdAssinatura(null);
        usuarioUseCase.salvar(usuario);

        log.info("Assinatura cancelada com sucesso.");
    }
}
