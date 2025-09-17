package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.PlanoUsuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssinaturaUseCaseTest {

    @Mock
    private AssinaturaGateway gateway;

    @Mock
    private UsuarioUseCase usuarioUseCase;

    @InjectMocks
    private AssinaturaUseCase useCase;

    private Assinatura assinatura;
    private Usuario usuario;

    @BeforeEach
    void setup() {
        assinatura = new Assinatura();
        assinatura.setIdUsuario("user-1");
        usuario = new Usuario();
        usuario.setId("user-1");
    }

    @Test
    void deveCriarNovaAssinaturaQuandoIdCustomerNull() {
        usuario.setIdCustomer(null);
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarCustom(assinatura)).thenReturn("cust-123");
        when(gateway.criarAssinaturaPlus("cust-123")).thenReturn("sub-456");

        String idRetornado = useCase.criar(assinatura);

        assertEquals("sub-456", idRetornado);

        assertEquals("cust-123", usuario.getIdCustomer());
        assertEquals(PlanoUsuario.PLUS, usuario.getPlano());
        assertEquals("sub-456", usuario.getIdAssinatura());
        InOrder ordem = inOrder(usuarioUseCase, gateway);
        ordem.verify(usuarioUseCase).consultarPorId("user-1");
        ordem.verify(gateway).criarCustom(assinatura);
        ordem.verify(gateway).criarAssinaturaPlus("cust-123");
        ordem.verify(usuarioUseCase).salvar(usuario);
    }

    @Test
    void deveCriarAssinaturaQuandoCustomerExistente() {
        usuario.setIdCustomer("cust-existente");
        when(usuarioUseCase.consultarPorId("user-1")).thenReturn(usuario);
        when(gateway.criarAssinaturaPlus("cust-existente")).thenReturn("sub-xyz");

        String idRetornado = useCase.criar(assinatura);

        assertEquals("sub-xyz", idRetornado);
        assertEquals("cust-existente", usuario.getIdCustomer()); // não alterou
        assertEquals(PlanoUsuario.PLUS, usuario.getPlano());
        assertEquals("sub-xyz", usuario.getIdAssinatura());
        verify(gateway, never()).criarCustom(any());
        verify(gateway).criarAssinaturaPlus("cust-existente");
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
        when(gateway.criarAssinaturaPlus("cust-ok"))
                .thenThrow(new DataProviderException("Erro ao criar uma assinatura.", null));

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
        usuario.setPlano(PlanoUsuario.PLUS);
        when(usuarioUseCase.consultarPorId("user-2")).thenReturn(usuario);

        assertDoesNotThrow(() -> useCase.cancelar("user-2"));

        assertNull(usuario.getIdAssinatura());
        assertEquals(PlanoUsuario.GRATIS, usuario.getPlano());
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