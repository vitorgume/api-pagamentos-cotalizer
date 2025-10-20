package com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.dataprovider;

import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Plano;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.mapper.PlanoMapper;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.PlanoRepository;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.repository.entity.PlanoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanoDataProviderTest {

    @Mock
    private PlanoRepository repository;

    @InjectMocks
    private PlanoDataProvider dataProvider;

    @Captor
    private ArgumentCaptor<String> idCaptor;

    @Test
    void consultarPorId_deveRetornarPlanoMapeado_quandoEncontrado() {
        String id = "abc123";
        PlanoEntity entity = mock(PlanoEntity.class);
        Plano domain = mock(Plano.class);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        try (MockedStatic<PlanoMapper> mocked = mockStatic(PlanoMapper.class)) {
            mocked.when(() -> PlanoMapper.paraDomain(entity)).thenReturn(domain);

            Optional<Plano> out = dataProvider.consultarPorId(id);

            assertTrue(out.isPresent());
            assertSame(domain, out.get());
            verify(repository, times(1)).findById(idCaptor.capture());
            assertEquals(id, idCaptor.getValue());
            verifyNoMoreInteractions(repository);
        }
    }

    @Test
    void consultarPorId_deveRetornarOptionalVazio_quandoNaoEncontrado() {
        String id = "nao-existe";
        when(repository.findById(id)).thenReturn(Optional.empty());

        try (MockedStatic<PlanoMapper> mocked = mockStatic(PlanoMapper.class)) {
            Optional<Plano> out = dataProvider.consultarPorId(id);

            assertTrue(out.isEmpty());
            verify(repository, times(1)).findById(id);
            verifyNoMoreInteractions(repository);
            mocked.verifyNoInteractions();
        }
    }

    @Test
    void consultarPorId_deveLancarDataProviderException_quandoRepositorioFalha() {
        String id = "qualquer";
        RuntimeException infra = new RuntimeException("falha mongo");
        when(repository.findById(id)).thenThrow(infra);

        DataProviderException ex = assertThrows(DataProviderException.class,
                () -> dataProvider.consultarPorId(id));

        assertEquals("Erro ao consultar plano pelo seu id.", ex.getMessage());
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }


}