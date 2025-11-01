package com.jefiro.thaurus_cnc.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de validação");
        response.put("fields", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
