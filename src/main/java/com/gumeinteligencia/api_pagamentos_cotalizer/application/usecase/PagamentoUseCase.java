package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PagamentoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.mapper.PagamentoMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoRequestDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PagamentoResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PagamentoUseCase {

    private final PagamentoGateway gateway;

    public Pagamento criar(String emailUsuario, BigDecimal valorAssinatura, String cardId, String cpf) {
        PagamentoRequestDto pagamentoRequestDto = PagamentoRequestDto.builder()
                .description("Plano Plus - Cotalizer")
                .installments(1)
                .payer(new PagamentoRequestDto.Payer(
                        emailUsuario,
                        PagamentoRequestDto.Identification.builder()
                                .type("CPF")
                                .number(cpf)
                                .build()
                ))
                .card_id(cardId)
                .transaction_amount(valorAssinatura)
                .build();

        PagamentoResponseDto response = gateway.enviarPagamento(pagamentoRequestDto);

        return PagamentoMapper.paraDomain(response);
    }
}
