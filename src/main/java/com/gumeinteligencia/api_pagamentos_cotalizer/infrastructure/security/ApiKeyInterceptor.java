package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.security;

import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.ApiKeyInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@Profile("!test")
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${security.api.key}")
    private final String API_KEY;

    public ApiKeyInterceptor(
            @Value("${security.api.key}") String API_KEY
    ) {
        this.API_KEY = API_KEY;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String receivedKey = request.getHeader("x-api-key");

        if (API_KEY.equals(receivedKey)) {
            return true;
        }

        throw new ApiKeyInvalidaException();
    }
}
