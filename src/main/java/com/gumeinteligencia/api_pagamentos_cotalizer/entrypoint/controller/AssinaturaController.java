package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.controller;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.usecase.AssinaturaUseCase;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.AssinaturaDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.ResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.mapper.AssinaturaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("assinaturas")
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaUseCase assinaturaUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<AssinaturaDto>> criar(@RequestBody AssinaturaDto novaAssinatura) {
        AssinaturaDto resultado = AssinaturaMapper.paraDto(assinaturaUseCase.criar(AssinaturaMapper.paraDomain(novaAssinatura)));
        ResponseDto<AssinaturaDto> response = new ResponseDto<>(resultado);
        return ResponseEntity.created(
                        UriComponentsBuilder
                                .newInstance()
                                .path("/assinaturas/{id}")
                                .buildAndExpand(resultado.getId())
                                .toUri())
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID idAssinatura) {
        assinaturaUseCase.deletar(idAssinatura);
        return ResponseEntity.noContent().build();
    }
}
