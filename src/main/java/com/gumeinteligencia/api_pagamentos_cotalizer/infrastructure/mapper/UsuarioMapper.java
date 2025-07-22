package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity paraEntity(Usuario domain) {
        return UsuarioEntity.builder()
                .id(domain.getId())
                .nome(domain.getNome())
                .email(domain.getEmail())
                .telefone(domain.getTelefone())
                .cpf(domain.getCpf())
                .cnpj(domain.getCnpj())
                .senha(domain.getSenha())
                .customerId(domain.getCustomerId())
                .build();
    }

    public static Usuario paraDomain(UsuarioEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .cpf(entity.getCpf())
                .cnpj(entity.getCnpj())
                .senha(entity.getSenha())
                .customerId(entity.getCustomerId())
                .build();
    }
}
