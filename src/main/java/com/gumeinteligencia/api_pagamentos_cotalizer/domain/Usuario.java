package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cnpj;
    private String senha;
    private StatusUsuario status;
    private String idCustomer;
    private Plano plano;
    private String idAssinatura;
    private String urlLogo;
    private Boolean feedback;
    private Integer quantidadeOrcamentos;
    private LocalDateTime dataCriacao;
    private TipoCadastro tipoCadastro;
    private Boolean onboarding;
}
