package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions;

public class DataProviderException extends RuntimeException {
    public DataProviderException(String mensagemErroSalvarAssinatura, Throwable cause) {
        super(mensagemErroSalvarAssinatura, cause);
    }
}
