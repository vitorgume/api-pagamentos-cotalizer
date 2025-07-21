package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private Identification identification;

    @AllArgsConstructor
    @Getter
    public static class Identification {
        private String type;
        private String number;
    }
}
