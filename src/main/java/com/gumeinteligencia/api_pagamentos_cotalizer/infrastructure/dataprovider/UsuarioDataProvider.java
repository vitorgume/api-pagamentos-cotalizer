package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.UsuarioGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper.UsuarioMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.UsuarioRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioDataProvider implements UsuarioGateway {

    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar usuário pelo seu id.";
    private final String MENSAGEM_ERRO_SALVAR_USUARIO = "Erro ao salvar usuário.";
    private final UsuarioRepository repository;

    @Override
    public Optional<Usuario> consultarPorId(String idUsuario) {
        Optional<UsuarioEntity> usuarioEntity;

        try {
            usuarioEntity = repository.findById(idUsuario);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
        }

        return usuarioEntity.map(UsuarioMapper::paraDomain);
    }

    @Override
    public void salvar(Usuario usuario) {
        try {
            repository.save(UsuarioMapper.paraEntity(usuario));
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_SALVAR_USUARIO, ex);
            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_USUARIO, ex.getCause());
        }
    }
}
