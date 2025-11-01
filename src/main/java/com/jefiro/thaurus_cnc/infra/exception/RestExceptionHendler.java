package com.jefiro.thaurus_cnc.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    private ResponseEntity<HandlerException> handleException(RecursoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerException(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(DadosInvalidosException.class)
    private ResponseEntity<HandlerException> handleException(DadosInvalidosException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerException(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(StatusInvalidoException.class)
    private ResponseEntity<HandlerException> handleException(StatusInvalidoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerException(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }



}
