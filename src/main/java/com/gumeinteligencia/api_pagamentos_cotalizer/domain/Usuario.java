package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cnpj;
    private String senha;
    private String tokenCardId;
    private String customerId;
}
