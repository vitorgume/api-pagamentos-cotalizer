package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.AssinaturaNaoEncontradaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.AssinaturaResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.dto.PlanoResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Pagamento;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssinaturaUseCase {

    private final AssinaturaGateway gateway;
    private final PagamentoUseCase pagamentoUseCase;
    private final UsuarioUseCase usuarioUseCase;
    private final MercadoPagoUseCase mercadoPagoUseCase;

    private final BigDecimal VALOR_ASSINATURA = BigDecimal.valueOf(1);

    public Assinatura criar(Assinatura assinatura, String tokenCardId) {
        PlanoResponseDto plano = mercadoPagoUseCase.criarPlano();
        AssinaturaResponseDto assinaturaSalva = mercadoPagoUseCase.criarAssinatura(plano.getId(), tokenCardId, assinatura.getEmailUsuario());

        Assinatura novaAssinatura = gateway.salvar(assinatura);

        return novaAssinatura;
    }

    public void cancelar(UUID idAssinatura) {
        this.consultarPorId(idAssinatura);
        gateway.deletar(idAssinatura);
    }

    private void consultarPorId(UUID idAssinatura) {
        Optional<Assinatura> assinatura = gateway.consultarPorId(idAssinatura);

        if(assinatura.isEmpty()) {
            throw new AssinaturaNaoEncontradaException();
        }
    }
}
