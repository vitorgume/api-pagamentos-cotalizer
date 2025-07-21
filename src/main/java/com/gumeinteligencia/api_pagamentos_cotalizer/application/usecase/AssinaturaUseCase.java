package com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.AssinaturaNaoEncontradaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.application.gateways.AssinaturaGateway;
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

    private final BigDecimal VALOR_ASSINATURA = BigDecimal.valueOf(39.90);

    public Assinatura criar(Assinatura assinatura, String tokenCardId) {
        Usuario usuario = usuarioUseCase.consultarPorId(assinatura.getIdUsuario());

        String customerId = mercadoPagoUseCase.criarCustomer(assinatura.getEmailUsuario(), assinatura.getCardholderName(), assinatura.getIdentification().getType(), assinatura.getIdentification().getNumber());

        String cardId = mercadoPagoUseCase.salvarCartao(customerId, tokenCardId);

        usuario.setCustomerId(customerId);
        usuario.setTokenCardId(cardId);

        usuarioUseCase.salvar(usuario);

        Pagamento pagamento = pagamentoUseCase.criar(assinatura.getEmailUsuario(), VALOR_ASSINATURA, cardId);
        assinatura.setDadosPagamento(pagamento);
        assinatura.setDataCriacao(LocalDateTime.now());
        assinatura.setUltimaRenovacao(LocalDateTime.now());
        assinatura.setValor(VALOR_ASSINATURA);

        Assinatura assinaturaSalva = gateway.salvar(assinatura);

        return assinaturaSalva;
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
