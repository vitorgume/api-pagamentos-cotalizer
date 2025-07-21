package com.gumeinteligencia.api_pagamentos_cotalizer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiPagamentosCotalizerApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("MERCADO_PAGO_ACESS_TOKEN", dotenv.get("MERCADO_PAGO_ACESS_TOKEN"));

		SpringApplication.run(ApiPagamentosCotalizerApplication.class, args);
	}

}
