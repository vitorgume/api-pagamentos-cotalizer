package com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.exceptionHandler;

import com.gumeinteligencia.api_pagamentos_cotalizer.application.exceptions.UsuarioNaoEncontradoException;
import com.gumeinteligencia.api_pagamentos_cotalizer.entrypoint.dto.ResponseDto;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.ApiKeyInvalidaException;
import com.gumeinteligencia.api_pagamentos_cotalizer.infrastructure.exceptions.DataProviderException;
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

    @ExceptionHandler(ApiKeyInvalidaException.class)
    public ResponseEntity<ResponseDto> apiKeyInvalidaExceptionHandler(ApiKeyInvalidaException exception) {
        ResponseDto.ErroDto erroDto = ResponseDto.ErroDto.builder().mensagens(List.of(exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.comErro(erroDto));
    }

    @ExceptionHandler(DataProviderException.class)
    public ResponseEntity<ResponseDto> dataProviderExceptionHandler(DataProviderException exception) {
        ResponseDto.ErroDto erroDto = ResponseDto.ErroDto.builder().mensagens(List.of(exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.comErro(erroDto));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> exceptionHandler(Exception exception) {
        ResponseDto.ErroDto erroDto = ResponseDto.ErroDto.builder().mensagens(List.of(exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.comErro(erroDto));
    }

}
