package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.PlanoUsuario;
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

    public String criar(Assinatura assinatura) {
        log.info("Criando assinatura para o usuario. Assinatura: {}", assinatura);

        Usuario usuario = usuarioUseCase.consultarPorId(assinatura.getIdUsuario());

        if(usuario.getIdCustomer() == null) {
            log.info("Criando customer do usuario.");

            String idCustomer = gateway.criarCustom(assinatura);
            usuario.setIdCustomer(idCustomer);

            log.info("Customer criado com sucesso.");
        }

        String idAssinatura = gateway.criarAssinatura(usuario.getIdCustomer());

        usuario.setPlano(PlanoUsuario.PLUS);
        usuario.setIdAssinatura(idAssinatura);
        usuarioUseCase.salvar(usuario);

        log.info("Assinatura para o usuario criada com sucesso. Id assinatura: {}", idAssinatura);

        return idAssinatura;
    }

    public void cancelar(String idUsuario) {
        log.error("Cancelando assinatura do usuario. Id usuario: {}", idUsuario);

        Usuario usuario = usuarioUseCase.consultarPorId(idUsuario);

        gateway.cancelar(usuario.getIdAssinatura());

        usuario.setPlano(PlanoUsuario.GRATIS);
        usuario.setIdAssinatura(null);
        usuarioUseCase.salvar(usuario);

        log.info("Assinatura cancelada com sucesso.");
    }
}
