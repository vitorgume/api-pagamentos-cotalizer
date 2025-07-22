package com.gumeinteligencia.api_pagamentos_cotalizer;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiPagamentosCotalizerApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		System.setProperty("STRIPE_ACESS_TOKEN", dotenv.get("STRIPE_ACESS_TOKEN"));
		System.setProperty("STRIPE_ASSINAUTRA_ID", dotenv.get("STRIPE_ASSINAUTRA_ID"));

		SpringApplication.run(ApiPagamentosCotalizerApplication.class, args);
	}

}
