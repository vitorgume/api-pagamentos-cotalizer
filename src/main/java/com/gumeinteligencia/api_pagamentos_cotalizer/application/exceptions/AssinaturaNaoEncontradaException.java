package com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions;

public class AssinaturaNaoEncontradaException extends RuntimeException {

    public AssinaturaNaoEncontradaException() {
        super("Assinatura não encontrada.");
    }
}
