package com.gumeinteligencia.api_pagamentos_cotalizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.stripe.com")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(30))))
                .build();
    }

}
