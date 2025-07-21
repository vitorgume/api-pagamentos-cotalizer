package com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;

import java.util.Optional;

public interface UsuarioGateway {
    Optional<Usuario> consultarPorId(String idUsuario);

    void salvar(Usuario usuario);
}
