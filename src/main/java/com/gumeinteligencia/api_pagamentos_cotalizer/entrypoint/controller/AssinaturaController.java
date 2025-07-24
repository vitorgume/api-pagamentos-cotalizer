package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.controller;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.AssinaturaUseCase;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.ResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper.AssinaturaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaUseCase assinaturaUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<Map<String, String>>> criar(@RequestBody AssinaturaDto novaAssinatura) {
        String resultado = assinaturaUseCase.criar(AssinaturaMapper.paraDomain(novaAssinatura));
        ResponseDto<Map<String, String>> response = new ResponseDto<>(Map.of("id_assinatura", resultado));

        return ResponseEntity.created(
                        UriComponentsBuilder
                                .newInstance()
                                .path("/assinaturas/{id}")
                                .buildAndExpand(resultado)
                                .toUri())
                .body(response);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> cancelar(@PathVariable("idUsuario") String idUsuario) {
        assinaturaUseCase.cancelar(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
