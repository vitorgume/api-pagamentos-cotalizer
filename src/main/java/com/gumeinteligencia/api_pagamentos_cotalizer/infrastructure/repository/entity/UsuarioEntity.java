package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.PlanoUsuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.StatusUsuario;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
    private PlanoUsuario plano;
    private String idAssinatura;
}
