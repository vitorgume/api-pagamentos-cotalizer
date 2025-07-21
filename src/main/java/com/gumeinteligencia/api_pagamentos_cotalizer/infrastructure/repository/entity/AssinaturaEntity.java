package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Pagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "Assinatura")
@Table(name = "assinaturas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AssinaturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_assinatura")
    private UUID id;
    private BigDecimal valor;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "ultima_renovacao")
    private LocalDateTime ultimaRenovacao;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Embedded
    private Pagamento dadosPagamento;
}
