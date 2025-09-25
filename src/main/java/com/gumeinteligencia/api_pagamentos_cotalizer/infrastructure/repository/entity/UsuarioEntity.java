package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.StatusUsuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoCadastro;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "usuraios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioEntity {

    @MongoId
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String cnpj;
    private String senha;
    private StatusUsuario status;
    private String idCustomer;
    private PlanoEntity plano;
    private String idAssinatura;
    private String urlLogo;
    private Boolean feedback;
    private Integer quantidadeOrcamentos;
    private LocalDateTime dataCriacao;
    private TipoCadastro tipoCadastro;
}
