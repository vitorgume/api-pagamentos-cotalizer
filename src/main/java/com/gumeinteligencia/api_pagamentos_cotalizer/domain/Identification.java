package com.gumeinteligencia.api_pagamentos_cotalizer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class Identification {
    private String type;
    private String number;
}
