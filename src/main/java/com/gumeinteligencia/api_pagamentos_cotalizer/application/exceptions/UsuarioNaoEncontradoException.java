package com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.");
    }
}
