package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AssinaturaDataProviderTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private AssinaturaDataProvider provider;

    private final String SECRET_KEY = "sk_test_123";
    private final String ASSINATURA_ID = "price_abc123";

    @Test
    public void deveCriarCustomerComSucesso() {
        Assinatura assinatura = new Assinatura();
        assinatura.setCustomerEmail("teste@email.com");
        assinatura.setPaymentMethodId("pm_123");

        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/v1/customers")).thenReturn(bodySpec);
        when(bodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(bodySpec);
        when(bodySpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(Map.of("id", "cus_abc123")));

        String id = provider.criarCustom(assinatura);

        assertEquals("cus_abc123", id);
    }


    @Test
    public void deveLancarExcecaoAoCriarCustomer() {
        Assinatura assinatura = new Assinatura();
        assinatura.setCustomerEmail("teste@email.com");
        assinatura.setPaymentMethodId("pm_123");

        when(webClient.post()).thenThrow(new RuntimeException("Erro simulado"));

        assertThrows(DataProviderException.class, () -> provider.criarCustom(assinatura));
    }

    @Test
    public void deveCriarAssinaturaComSucesso() {
        String customerId = "cus_abc123";

        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/v1/subscriptions")).thenReturn(bodySpec);
        when(bodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(bodySpec);
        when(bodySpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(Map.of("id", "sub_456")));

        String id = provider.criarAssinatura(customerId);

        assertEquals("sub_456", id);
    }

    @Test
    public void deveLancarExcecaoAoCriarAssinatura() {
        when(webClient.post()).thenThrow(new RuntimeException("Erro"));

        assertThrows(DataProviderException.class, () -> provider.criarAssinatura("cus_erro"));
    }

    @Test
    public void deveCriarAssinaturaEnterpriseComSucesso() {
        String customerId = "cus_abc123";

        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/v1/subscriptions")).thenReturn(bodySpec);
        when(bodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(bodySpec);
        when(bodySpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(Map.of("id", "sub_456")));

        String id = provider.criarAssinaturaEnterprise(customerId);

        assertEquals("sub_456", id);
    }

    @Test
    public void deveLancarExcecaoAoCriarAssinaturaEnterprise() {
        when(webClient.post()).thenThrow(new RuntimeException("Erro"));

        assertThrows(DataProviderException.class, () -> provider.criarAssinaturaEnterprise("cus_erro"));
    }

    @Test
    public void deveCobrirLambdaOnStatusNaCriacaoDeAssinatura() {
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec= mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ClientResponse mockClientResponse = mock(ClientResponse.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/v1/subscriptions")).thenReturn(bodySpec);
        when(bodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(bodySpec);
        when(bodySpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(mockClientResponse.bodyToMono(String.class))
                .thenReturn(Mono.just("ERRO_DE_TESTE_STRIPE"));

        when(responseSpec.onStatus(any(), any()))
                .thenAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    Function<ClientResponse, Mono<? extends Throwable>> errorFunction =
                            invocation.getArgument(1);
                    Mono<? extends Throwable> monoErr = errorFunction.apply(mockClientResponse);
                    throw monoErr.block();
                });
        DataProviderException ex = assertThrows(DataProviderException.class, () -> {
            provider.criarAssinatura("qualquer-customer-id");
        });

        assertTrue(ex.getMessage().contains("Erro ao criar uma assinatura."));
    }

    @Test
    public void deveCobrirLambdaOnStatusNaCriacaoDeAssinaturaEnterprise() {
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec= mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ClientResponse mockClientResponse = mock(ClientResponse.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri("/v1/subscriptions")).thenReturn(bodySpec);
        when(bodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).thenReturn(bodySpec);
        when(bodySpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(mockClientResponse.bodyToMono(String.class))
                .thenReturn(Mono.just("ERRO_DE_TESTE_STRIPE"));

        when(responseSpec.onStatus(any(), any()))
                .thenAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    Function<ClientResponse, Mono<? extends Throwable>> errorFunction =
                            invocation.getArgument(1);
                    Mono<? extends Throwable> monoErr = errorFunction.apply(mockClientResponse);
                    throw monoErr.block();
                });
        DataProviderException ex = assertThrows(DataProviderException.class, () -> {
            provider.criarAssinaturaEnterprise("qualquer-customer-id");
        });

        assertTrue(ex.getMessage().contains("Erro ao criar uma assinatura."));
    }




    @Test
    public void deveCancelarAssinaturaComSucesso() {
        WebClient.RequestHeadersUriSpec<?> uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.delete()).thenAnswer(invocation -> uriSpec);
        when(uriSpec.uri("/v1/subscriptions/sub_123")).thenAnswer(invocation -> headersSpec);
        when(headersSpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(Map.of()));

        assertDoesNotThrow(() -> provider.cancelar("sub_123"));
    }

    @Test
    public void deveLancarExcecaoAoCancelarAssinatura() {
        when(webClient.delete()).thenThrow(new RuntimeException("Erro"));

        assertThrows(DataProviderException.class, () -> provider.cancelar("sub_falha"));
    }

    @Test
    public void deveCobrirLambdaOnStatusNoCancelarAssinatura() {
        WebClient.RequestHeadersUriSpec<?> uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ClientResponse mockResponse = mock(ClientResponse.class);

        when(webClient.delete()).thenAnswer(invocation -> uriSpec);
        when(uriSpec.uri("/v1/subscriptions/sub_123")).thenAnswer(invocation -> headersSpec);
        when(headersSpec.header(eq(HttpHeaders.AUTHORIZATION), anyString())).thenAnswer(invocation -> headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(mockResponse.bodyToMono(String.class))
                .thenReturn(Mono.just("ERRO_DE_TESTE_STRIPE"));

        when(responseSpec.onStatus(any(Predicate.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    Function<ClientResponse, Mono<? extends Throwable>> fn =
                            invocation.getArgument(1);
                    throw fn.apply(mockResponse).block();
                });

        DataProviderException ex = assertThrows(DataProviderException.class, () -> {
            provider.cancelar("sub_123");
        });

        assertTrue(ex.getMessage().contains("Erro ao cancelar assinatura"));
    }
}