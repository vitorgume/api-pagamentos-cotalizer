package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Document(collection = "planos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlanoEntity {

    @MongoId
    private String id;
    private String titulo;
    private BigDecimal valor;
    private Integer limite;
    private String idPlanoStripe;
    private Boolean padrao;
    private Integer grau;

}
