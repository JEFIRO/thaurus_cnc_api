package com.jefiro.thaurus_cnc.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class HandlerException {
    HttpStatus status;
    String message;
}
