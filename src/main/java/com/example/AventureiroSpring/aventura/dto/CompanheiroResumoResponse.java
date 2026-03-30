package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eEspecie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanheiroResumoResponse {
    private Long id;
    private String nome;
    private eEspecie especie;
    private int lealdade;
}