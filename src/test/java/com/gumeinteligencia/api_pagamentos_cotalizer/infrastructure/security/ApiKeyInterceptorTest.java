package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.security;

import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.ApiKeyInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiKeyInterceptorTest {

    private final String CHAVE_TESTE = "chave-teste-123";
    private ApiKeyInterceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new ApiKeyInterceptor(CHAVE_TESTE);
    }

    @Test
    void deveValidarVerdadeiroAChaveDaApi() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("x-api-key")).thenReturn(CHAVE_TESTE);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
    }

    @Test
    public void deveLancarExcecaoComApiKeyInvalida() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader("x-api-key")).thenReturn("chave-invalida");

        assertThrows(ApiKeyInvalidaException.class, () -> {
            interceptor.preHandle(request, response, new Object());
        });
    }
}