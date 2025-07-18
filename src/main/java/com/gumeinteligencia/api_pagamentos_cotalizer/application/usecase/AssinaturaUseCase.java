package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.AssinaturaNaoEncontradaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Assinatura;
import com.gumeinteligencia.api_pagamentos_cotalizer.domain.Pagamento;
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

    @Value("${cotalizer.valor.assinatura}")
    private final BigDecimal VALOR_ASSINATURA;

    public Assinatura criar(Assinatura assinatura) {
        Pagamento pagamento = pagamentoUseCase.criar(assinatura.getTokenCardId(), assinatura.getEmailUsuario(), VALOR_ASSINATURA);
        assinatura.setDadosPagamento(pagamento);
        assinatura.setDataCriacao(LocalDateTime.now());
        assinatura.setUltimaRenovacao(LocalDateTime.now());
        assinatura.setValor(VALOR_ASSINATURA);

        Assinatura assinaturaSalva = gateway.salvar(assinatura);

        return assinaturaSalva;
    }

    public void deletar(UUID idAssinatura) {
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
