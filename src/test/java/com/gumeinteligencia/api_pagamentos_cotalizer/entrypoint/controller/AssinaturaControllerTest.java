package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.controller;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.AssinaturaUseCase;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.PlanoRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AssinaturaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AssinaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssinaturaUseCase assinaturaUseCase;

    @MockitoBean
    private PlanoRepository planoRepository;

    @Test
    @DisplayName("POST /assinaturas → 201 Created, Location header e corpo com id_assinatura")
    void criarDeveRetornarCreatedComLocationEBody() throws Exception {
        given(assinaturaUseCase.criar(any())).willReturn("sub-456");
        given(planoRepository.findById(Mockito.anyString())).willReturn(Optional.of(PlanoEntity.builder().id("teste123").idPlanoStripe("testestr").build()));

        String jsonBody = """
            {
              "idUsuario": "user-1",
              "customerEmail": "teste@exemplo.com",
              "paymentMethodId": "pm_123",
              "plano": {
                "id": "teste123"
              }
            }
            """;

        mockMvc.perform(post("/assinaturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/assinaturas/sub-456"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dado.id_assinatura", is("sub-456")));

        ArgumentCaptor<Assinatura> captor = ArgumentCaptor.forClass(Assinatura.class);
        then(assinaturaUseCase).should().criar(captor.capture());
        Assinatura dtoPassado = captor.getValue();
        assertThat(dtoPassado.getIdUsuario(), is("user-1"));
        assertThat(dtoPassado.getCustomerEmail(), is("teste@exemplo.com"));
        assertThat(dtoPassado.getPaymentMethodId(), is("pm_123"));
    }

    @Test
    @DisplayName("DELETE /assinaturas/{idUsuario} → 204 No Content")
    void cancelarDeveRetornarNoContent() throws Exception {
        willDoNothing().given(assinaturaUseCase).cancelar("user-1");

        mockMvc.perform(delete("/assinaturas/user-1"))
                .andExpect(status().isNoContent());

        then(assinaturaUseCase).should().cancelar("user-1");
    }
}