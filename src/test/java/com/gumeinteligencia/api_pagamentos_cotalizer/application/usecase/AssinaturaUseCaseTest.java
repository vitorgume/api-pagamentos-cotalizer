package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssinaturaUseCaseTest {

    @Mock
    private AssinaturaGateway gateway;

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @Mock
    private PlanoUseCase planoUseCase;

    @InjectMocks
    private AssinaturaUseCase useCase;

    private Assinatura assinatura;
    private Usuario usuario;
    private Plano plano;

    @BeforeEach
    void setup() {
        assinatura = new Assinatura();
        assinatura.setIdUsuario("user-1");
        assinatura.setIdPlano("idteste");
        usuario = new Usuario();
        usuario.setId("user-1");
        plano = Plano.builder().id("idteste").idPlanoStripe("testestr").build();
        usuario.setPlano(plano);
    }

    @Test
    void deveCriarNovaAssinaturaQuandoIdCustomerNull() {
        usuario.setIdCustomer(null);
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarCustom(assinatura)).thenReturn("cust-123");
        when(gateway.criarAssinatura("cust-123", plano.getIdPlanoStripe())).thenReturn("sub-456");
        when(planoUseCase.consultarPorId("idteste")).thenReturn(plano);

        String idRetornado = useCase.criar(assinatura);

        assertEquals("sub-456", idRetornado);

        assertEquals("cust-123", usuario.getIdCustomer());
        assertEquals(plano.getId(), usuario.getPlano().getId());
        assertEquals("sub-456", usuario.getIdAssinatura());
        InOrder ordem = inOrder(usuarioUseCase, gateway);
        ordem.verify(usuarioUseCase).consultarPorId("user-1");
        ordem.verify(gateway).criarCustom(assinatura);
        ordem.verify(gateway).criarAssinatura("cust-123", plano.getIdPlanoStripe());
        ordem.verify(usuarioUseCase).salvar(usuario);
    }

    @Test
    void deveCriarAssinaturaQuandoCustomerExistente() {
        usuario.setIdCustomer("cust-existente");
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarAssinatura("cust-existente", plano.getIdPlanoStripe())).thenReturn("sub-xyz");
        when(planoUseCase.consultarPorId(Mockito.anyString())).thenReturn(plano);

        String idRetornado = useCase.criar(assinatura);

        assertEquals("sub-xyz", idRetornado);
        assertEquals("cust-existente", usuario.getIdCustomer());
        assertEquals(plano.getId(), usuario.getPlano().getId());
        assertEquals("sub-xyz", usuario.getIdAssinatura());
        verify(gateway, never()).criarCustom(any());
        verify(gateway).criarAssinatura("cust-existente", plano.getIdPlanoStripe());
        verify(usuarioUseCase).salvar(usuario);
    }

    @Test
    void devePropagarExceptionQuandoCriarCustomFalha() {
        usuario.setIdCustomer(null);
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarCustom(assinatura))
                .thenThrow(new DataProviderException("Erro ao criar customer.", null));

        DataProviderException ex = assertThrows(
                DataProviderException.class,
                () -> useCase.criar(assinatura)
        );
        assertTrue(ex.getMessage().contains("Erro ao criar customer."));
    }

    @Test
    void devePropagarExceptionQuandoCriarAssinaturaFalha() {
        usuario.setIdCustomer("cust-ok");
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarAssinatura("cust-ok", plano.getIdPlanoStripe()))
                .thenThrow(new DataProviderException("Erro ao criar uma assinatura.", null));
        when(planoUseCase.consultarPorId(plano.getId())).thenReturn(plano);

        DataProviderException ex = assertThrows(
                DataProviderException.class,
                () -> useCase.criar(assinatura)
        );
        assertTrue(ex.getMessage().contains("Erro ao criar uma assinatura."));
    }

    @Test
    void deveCancelarAssinaturaComSucesso() {
        usuario.setId("user-2");
        usuario.setIdAssinatura("sub-999");
        usuario.setPlano(plano);
        when(usuarioUseCase.consultarPorId("user-2")).thenReturn(usuario);
        when(planoUseCase.consultarPlanoPadrao()).thenReturn(plano);

        assertDoesNotThrow(() -> useCase.cancelar("user-2"));

        assertNull(usuario.getIdAssinatura());
        assertEquals(plano.getId(), usuario.getPlano().getId());
        InOrder ordem = inOrder(usuarioUseCase, gateway);
        ordem.verify(usuarioUseCase).consultarPorId("user-2");
        ordem.verify(gateway).cancelar("sub-999");
        ordem.verify(usuarioUseCase).salvar(usuario);
    }

    @Test
    void devePropagarExceptionQuandoCancelarFalha() {
        usuario.setId("user-3");
        usuario.setIdAssinatura("sub-erro");
        when(usuarioUseCase.consultarPorId("user-3")).thenReturn(usuario);
        doThrow(new DataProviderException("Erro ao cancelar assinatura.", null))
                .when(gateway).cancelar("sub-erro");

        DataProviderException ex = assertThrows(
                DataProviderException.class,
                () -> useCase.cancelar("user-3")
        );
        assertTrue(ex.getMessage().contains("Erro ao cancelar assinatura."));
    }
}