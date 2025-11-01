package com.jefiro.thaurus_cnc.infra.exception;

public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException(String message) {super(message);}

    public DadosInvalidosException() {
        super("Campos obrigatórios ausentes ou inválidos.");
    }
}
