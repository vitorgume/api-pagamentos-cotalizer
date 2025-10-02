package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper.UsuarioMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.UsuarioRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioDataProviderTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioDataProvider dataProvider;

    @Test
    void deveConsultarUsuarioPorIdComSucesso() {
        String id = "usuario-123";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(id);
        usuarioEntity.setPlano(PlanoEntity.builder().id("teste123").build());

        when(repository.findById(id)).thenReturn(Optional.of(usuarioEntity));

        Optional<Usuario> resultado = dataProvider.consultarPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void deveLancarExcecaoAoConsultarUsuarioPorId() {
        String id = "usuario-erro";

        when(repository.findById(id)).thenThrow(new RuntimeException("Erro simulado"));

        DataProviderException excecao = assertThrows(DataProviderException.class, () -> {
            dataProvider.consultarPorId(id);
        });

        assertTrue(excecao.getMessage().contains("Erro ao consultar usuário"));
    }

    @Test
    public void deveSalvarUsuarioComSucesso() {
        Usuario usuario = new Usuario();
        usuario.setId("usuario-abc");
        usuario.setPlano(Plano.builder().id("teste123").build());

        UsuarioEntity entity = UsuarioMapper.paraEntity(usuario);

        when(repository.save(Mockito.any(UsuarioEntity.class))).thenReturn(entity);

        assertDoesNotThrow(() -> dataProvider.salvar(usuario));

        verify(repository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    public void deveLancarExcecaoAoSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId("usuario-exception");
        usuario.setPlano(Plano.builder().id("teste123").build());

        when(repository.save(any())).thenThrow(new RuntimeException("Erro ao salvar"));

        DataProviderException excecao = assertThrows(DataProviderException.class, () -> {
            dataProvider.salvar(usuario);
        });

        assertTrue(excecao.getMessage().contains("Erro ao salvar usuário"));
    }
}