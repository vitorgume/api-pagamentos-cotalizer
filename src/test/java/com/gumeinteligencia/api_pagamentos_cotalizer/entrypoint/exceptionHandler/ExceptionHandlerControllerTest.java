package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.exceptionHandler;


import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.ResponseDto;

import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.ApiKeyInvalidaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerControllerTest {
    private ExceptionHandlerController controller;

    @BeforeEach
    void setup() {
        controller = new ExceptionHandlerController();
    }

    @Test
    void usuarioNaoEncontradoExceptionHandler_deveRetornar404ComErro() {
        UsuarioNaoEncontradoException ex = new UsuarioNaoEncontradoException();

        ResponseEntity<ResponseDto> response = controller
                .usuarioNaoEncontradoExceptionHandler(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ResponseDto body = response.getBody();
        assertNotNull(body);
        ResponseDto.ErroDto erro = body.getErro();
        assertNotNull(erro);
        assertEquals(List.of("Usuário não encontrado."), erro.getMensagens());
    }

    @Test
    void apiKeyInvalidaExceptionHandler_deveRetornar401ComErro() {
        ApiKeyInvalidaException ex = new ApiKeyInvalidaException();

        ResponseEntity<ResponseDto> response = controller
                .apiKeyInvalidaExceptionHandler(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        ResponseDto body = response.getBody();
        assertNotNull(body);
        ResponseDto.ErroDto erro = body.getErro();
        assertNotNull(erro);
        assertEquals(List.of("Chave de api inválida"), erro.getMensagens());
    }

    @Test
    void dataProviderExceptionHandler_deveRetornar500ComErro() {
        String msg = "Falha no DataProvider";
        DataProviderException ex = new DataProviderException(msg, new RuntimeException());

        ResponseEntity<ResponseDto> response = controller
                .dataProviderExceptionHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ResponseDto body = response.getBody();
        assertNotNull(body);
        ResponseDto.ErroDto erro = body.getErro();
        assertNotNull(erro);
        assertEquals(List.of(msg), erro.getMensagens());
    }

    @Test
    void exceptionHandler_generica_deveRetornar500ComErro() {
        String msg = "Alguma exceção genérica";
        Exception ex = new Exception(msg);

        ResponseEntity<ResponseDto> response = controller
                .exceptionHandler(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ResponseDto body = response.getBody();
        assertNotNull(body);
        ResponseDto.ErroDto erro = body.getErro();
        assertNotNull(erro);
        assertEquals(List.of(msg), erro.getMensagens());
    }

}