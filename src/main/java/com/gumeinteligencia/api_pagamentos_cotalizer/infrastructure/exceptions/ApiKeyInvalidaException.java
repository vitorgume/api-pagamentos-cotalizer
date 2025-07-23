package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions;

public class ApiKeyInvalidaException extends RuntimeException {
    public ApiKeyInvalidaException() {
        super("Chave de api inválida");
    }
}
