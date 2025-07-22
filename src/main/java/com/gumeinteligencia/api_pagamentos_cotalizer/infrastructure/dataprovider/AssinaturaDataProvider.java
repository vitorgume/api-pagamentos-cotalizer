package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.AssinaturaRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.AssinaturaEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class AssinaturaDataProvider implements AssinaturaGateway {

    private final AssinaturaRepository repository;

    private final WebClient webClient;

    @Value("${stripe.acess.token}")
    private final String SECRET_KEY;

    @Value("${stripe.assinatura.id}")
    private final String ASSINATURA_ID;

    public AssinaturaDataProvider(
            AssinaturaRepository repository,
            WebClient webClient,
            @Value("${stripe.acess.token}") String SECRET_KEY,
            @Value("${stripe.assinatura.id}") String ASSINATURA_ID
    ) {
        this.repository = repository;
        this.webClient = webClient;
        this.SECRET_KEY = SECRET_KEY;
        this.ASSINATURA_ID = ASSINATURA_ID;
    }


    private final String MENSAGEM_ERRO_SALVAR_ASSINATURA = "Erro ao salvar assinatura.";
    private final String MENSAGEM_ERRO_DELETAR_ASSINATURA = "Erro ao deletar assinatura.";
    private final String MENSAGEM_ERRO_CONSULTAR_POR_ID = "Erro ao consultar assinatura pelo seu id.";



    @Override
    public Assinatura salvar(Assinatura assinatura) {
//        AssinaturaEntity assinaturaEntity = AssinaturaMapper.paraEntity(assinatura);
//
//        try {
//            assinaturaEntity = repository.save(assinaturaEntity);
//        } catch (Exception ex) {
//            log.error(MENSAGEM_ERRO_SALVAR_ASSINATURA, ex);
//            throw new DataProviderException(MENSAGEM_ERRO_SALVAR_ASSINATURA, ex.getCause());
//        }
//
//        return AssinaturaMapper.paraDomain(assinaturaEntity);
        return null;
    }

    @Override
    public void deletar(UUID idAssinatura) {
        try {
            repository.deleteById(idAssinatura);
        } catch (Exception ex) {
            log.error(MENSAGEM_ERRO_DELETAR_ASSINATURA, ex);
            throw new DataProviderException(MENSAGEM_ERRO_DELETAR_ASSINATURA, ex.getCause());
        }
    }

    @Override
    public Optional<Assinatura> consultarPorId(UUID idAssinatura) {
//        Optional<AssinaturaEntity> assinaturaEntity;
//
//        try {
//            assinaturaEntity = repository.findById(idAssinatura);
//        } catch (Exception ex) {
//            log.error(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex);
//            throw new DataProviderException(MENSAGEM_ERRO_CONSULTAR_POR_ID, ex.getCause());
//        }
//
//        return assinaturaEntity.map(AssinaturaMapper::paraDomain);
        return null;
    }

    @Override
    public String criarCustom(Assinatura assinatura) {
        Map response = webClient.post()
                .uri("https://api.stripe.com/v1/customers")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                .bodyValue("email=" + assinatura.getCustomerEmail() + "&payment_method=" + assinatura.getPaymentMethodId() + "&invoice_settings[default_payment_method]=" + assinatura.getPaymentMethodId())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return response.get("id").toString();
    }

    @Override
    public void criarAssinatura(String customerId) {
        webClient.post()
                .uri("https://api.stripe.com/v1/subscriptions")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SECRET_KEY)
                .bodyValue("customer=" + customerId + "&items[0][price]=" + ASSINATURA_ID)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
