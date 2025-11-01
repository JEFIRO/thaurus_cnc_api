package com.jefiro.thaurus_cnc.infra.exception;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException() {
        super("O recurso solicitado não existe no servidor.");
    }

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
