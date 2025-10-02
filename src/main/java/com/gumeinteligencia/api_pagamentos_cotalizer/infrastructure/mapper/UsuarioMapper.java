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
                .status(domain.getStatus())
                .idCustomer(domain.getIdCustomer())
                .plano(PlanoMapper.paraEntity(domain.getPlano()))
                .idAssinatura(domain.getIdAssinatura())
                .quantidadeOrcamentos(domain.getQuantidadeOrcamentos())
                .dataCriacao(domain.getDataCriacao())
                .tipoCadastro(domain.getTipoCadastro())
                .urlLogo(domain.getUrlLogo())
                .feedback(domain.getFeedback())
                .quantidadeOrcamentos(domain.getQuantidadeOrcamentos())
                .dataCriacao(domain.getDataCriacao())
                .tipoCadastro(domain.getTipoCadastro())
                .onboarding(domain.getOnboarding())
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
                .status(entity.getStatus())
                .idCustomer(entity.getIdCustomer())
                .plano(PlanoMapper.paraDomain(entity.getPlano()))
                .idAssinatura(entity.getIdAssinatura())
                .urlLogo(entity.getUrlLogo())
                .feedback(entity.getFeedback())
                .quantidadeOrcamentos(entity.getQuantidadeOrcamentos())
                .dataCriacao(entity.getDataCriacao())
                .tipoCadastro(entity.getTipoCadastro())
                .onboarding(entity.getOnboarding())
                .build();
    }
}
