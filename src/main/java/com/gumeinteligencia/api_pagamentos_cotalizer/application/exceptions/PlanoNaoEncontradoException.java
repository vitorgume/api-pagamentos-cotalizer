package com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions;

public class PlanoNaoEncontradoException extends RuntimeException {

    public PlanoNaoEncontradoException() {
        super("Plano não encontrado.");
    }
}
