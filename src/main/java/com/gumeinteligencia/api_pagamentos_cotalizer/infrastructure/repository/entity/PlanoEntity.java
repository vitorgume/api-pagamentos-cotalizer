package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoPlano;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "planos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlanoEntity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String titulo;
    private BigDecimal valor;
    private Integer limite;
    private String idPlanoStripe;
    private TipoPlano tipoPlano;
    private Integer sequencia;
    private List<String> servicos;

}
