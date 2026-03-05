package com.example.AventureiroSpring.Exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ValidationException extends RuntimeException {
    private final List<String> erros;

    public ValidationException(List<String> erros) {
        super("Solicitação inválida");
        this.erros = erros;
    }
}