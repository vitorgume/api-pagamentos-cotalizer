package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.UsuarioGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private UsuarioGateway gateway;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("teste-123");
    }

    @Test
    void deveConsultarPorId() {
        Mockito.when(gateway.consultarPorId(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioUseCase.consultarPorId("teste-123");

        Assertions.assertEquals(resultado.getId(), usuario.getId());
    }

    @Test
    void deveLancarExceptionCasoUsuarioSejaNull() {
        Mockito.when(gateway.consultarPorId(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioUseCase.consultarPorId("teste-123"));
    }

    @Test
    void deveSalvarUsuario() {
        usuarioUseCase.salvar(usuario);
        Mockito.verify(gateway, Mockito.times(1)).salvar(Mockito.any());
    }
}