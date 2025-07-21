package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.MercadoPagoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.CustomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoUseCase {

    private final MercadoPagoGateway gateway;

    public String criarCustomer(String email, String fullName, String identificationType, String identificationNumber) {
        CustomRequestDto body = CustomRequestDto.builder()
                .email(email)
                .firstName(fullName.split(" ")[0])
                .lastName(fullName.substring(fullName.indexOf(" ") + 1))
                .identification(new CustomRequestDto.Identification(
                        identificationType,
                        identificationNumber
                ))
                .build();

        String idCustomer = gateway.criarCustomer(body);

        return idCustomer;
    }

    public String salvarCartao(String customerId, String tokenCardId) {
        return gateway.salvarCartao(customerId, tokenCardId);
    }
}
