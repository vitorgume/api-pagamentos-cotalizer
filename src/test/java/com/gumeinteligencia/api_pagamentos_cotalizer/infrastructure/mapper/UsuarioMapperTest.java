package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.PlanoUsuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.StatusUsuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private Usuario usuarioDomain;
    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioDomain = Usuario.builder()
                .id(UUID.randomUUID().toString())
                .nome("Usuario teste")
                .email("emailteste@gmail.com")
                .telefone("5544000000000")
                .cpf("12345678987")
                .cnpj("123456789000132")
                .senha("senha123")
                .status(StatusUsuario.ATIVO)
                .idCustomer(UUID.randomUUID().toString())
                .plano(PlanoUsuario.PLUS)
                .idAssinatura(UUID.randomUUID().toString())
                .build();

        usuarioEntity = UsuarioEntity.builder()
                .id(UUID.randomUUID().toString())
                .nome("Usuario teste entity")
                .email("emailtesteentity@gmail.com")
                .telefone("5544000000032")
                .cpf("12345678978")
                .cnpj("123456789000123")
                .senha("senha312")
                .status(StatusUsuario.INATIVO)
                .idCustomer(UUID.randomUUID().toString())
                .plano(PlanoUsuario.GRATIS)
                .idAssinatura(UUID.randomUUID().toString())
                .build();
    }

    @Test
    void deveRetornarEntity() {
        UsuarioEntity resultado = UsuarioMapper.paraEntity(usuarioDomain);

        Assertions.assertEquals(resultado.getId(), usuarioDomain.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioDomain.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioDomain.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioDomain.getTelefone());
        Assertions.assertEquals(resultado.getCpf(), usuarioDomain.getCpf());
        Assertions.assertEquals(resultado.getCnpj(), usuarioDomain.getCnpj());
        Assertions.assertEquals(resultado.getSenha(), usuarioDomain.getSenha());
        Assertions.assertEquals(resultado.getStatus(), usuarioDomain.getStatus());
        Assertions.assertEquals(resultado.getIdCustomer(), usuarioDomain.getIdCustomer());
        Assertions.assertEquals(resultado.getPlano(), usuarioDomain.getPlano());
        Assertions.assertEquals(resultado.getIdAssinatura(), usuarioDomain.getIdAssinatura());
    }

    @Test
    void deveRetornarDomain() {

        Usuario resultado = UsuarioMapper.paraDomain(usuarioEntity);

        Assertions.assertEquals(resultado.getId(), usuarioEntity.getId());
        Assertions.assertEquals(resultado.getNome(), usuarioEntity.getNome());
        Assertions.assertEquals(resultado.getEmail(), usuarioEntity.getEmail());
        Assertions.assertEquals(resultado.getTelefone(), usuarioEntity.getTelefone());
        Assertions.assertEquals(resultado.getCpf(), usuarioEntity.getCpf());
        Assertions.assertEquals(resultado.getCnpj(), usuarioEntity.getCnpj());
        Assertions.assertEquals(resultado.getSenha(), usuarioEntity.getSenha());
        Assertions.assertEquals(resultado.getStatus(), usuarioEntity.getStatus());
        Assertions.assertEquals(resultado.getIdCustomer(), usuarioEntity.getIdCustomer());
        Assertions.assertEquals(resultado.getPlano(), usuarioEntity.getPlano());
        Assertions.assertEquals(resultado.getIdAssinatura(), usuarioEntity.getIdAssinatura());
    }
}