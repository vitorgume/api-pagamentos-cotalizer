package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.UsuarioGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway gateway;

    public Usuario consultarPorId(String idUsuario) {
        Optional<Usuario> usuario = gateway.consultarPorId(idUsuario);

        if(usuario.isEmpty()) {
            throw new UsuarioNaoEncontradoException();
        }

        return usuario.get();
    }

    public void salvar(Usuario usuario) {
        this.gateway.salvar(usuario);
    }
}
