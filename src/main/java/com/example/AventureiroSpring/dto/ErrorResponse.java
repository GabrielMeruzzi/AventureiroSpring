package com.example.AventureiroSpring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ErrorResponse {
    private String mensagem;
    private List<String> detalhes;

    public ErrorResponse(String mensagem, List<String> detalhes) {
        this.mensagem = mensagem;
        this.detalhes = detalhes;
    }

}