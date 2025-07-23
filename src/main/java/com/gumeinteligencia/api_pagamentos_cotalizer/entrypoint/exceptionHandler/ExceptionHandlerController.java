package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.exceptionHandler;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ResponseDto> usuarioNaoEncontradoExceptionHandler(UsuarioNaoEncontradoException exception) {
        ResponseDto.ErroDto erroDto = ResponseDto.ErroDto.builder().mensagens(List.of(exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.comErro(erroDto));
    }

}
