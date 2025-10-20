package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.PlanoNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.PlanoGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoCadastro;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.TipoPlano;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoUseCaseTest {

    @Mock
    private PlanoGateway gateway;

    @InjectMocks
    private PlanoUseCase useCase;

    @Test
    void consultarPorId_deveRetornarPlano_quandoEncontrado() {
        // arrange
        String id = "plano-123";
        Plano plano = mock(Plano.class);
        when(gateway.consultarPorId(id)).thenReturn(Optional.of(plano));

        // act
        Plano out = useCase.consultarPorId(id);

        // assert
        assertNotNull(out);
        assertSame(plano, out);
        verify(gateway, times(1)).consultarPorId(id);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void consultarPorId_deveLancarPlanoNaoEncontrado_quandoVazio() {
        // arrange
        String id = "inexistente";
        when(gateway.consultarPorId(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(PlanoNaoEncontradoException.class, () -> useCase.consultarPorId(id));
        verify(gateway, times(1)).consultarPorId(id);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void consultarPorId_devePropagarExcecao_quandoGatewayFalha() {
        // arrange
        String id = "qualquer";
        IllegalStateException infra = new IllegalStateException("falha");
        when(gateway.consultarPorId(id)).thenThrow(infra);

        // act + assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> useCase.consultarPorId(id));
        assertSame(infra, thrown);
        verify(gateway, times(1)).consultarPorId(id);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void consultarPlanoPadrao_deveRetornarPlano_quandoEncontrado() {
        // arrange
        Plano plano = mock(Plano.class);
        when(gateway.consultarPlanoPeloTipo(TipoPlano.PADRAO)).thenReturn(Optional.of(plano));

        // act
        Plano out = useCase.consultarPlanoPadrao();

        // assert
        assertNotNull(out);
        assertSame(plano, out);
        verify(gateway, times(1)).consultarPlanoPeloTipo(TipoPlano.PADRAO);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void consultarPlanoPadrao_deveLancarPlanoNaoEncontrado_quandoVazio() {
        // arrange
        when(gateway.consultarPlanoPeloTipo(TipoPlano.PADRAO)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(PlanoNaoEncontradoException.class, () -> useCase.consultarPlanoPadrao());
        verify(gateway, times(1)).consultarPlanoPeloTipo(TipoPlano.PADRAO);
        verifyNoMoreInteractions(gateway);
    }

    @Test
    void consultarPlanoPadrao_devePropagarExcecao_quandoGatewayFalha() {
        // arrange
        RuntimeException infra = new RuntimeException("erro");
        when(gateway.consultarPlanoPeloTipo(TipoPlano.PADRAO)).thenThrow(infra);

        // act + assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> useCase.consultarPlanoPadrao());
        assertSame(infra, thrown);
        verify(gateway, times(1)).consultarPlanoPeloTipo(TipoPlano.PADRAO);
        verifyNoMoreInteractions(gateway);
    }

}