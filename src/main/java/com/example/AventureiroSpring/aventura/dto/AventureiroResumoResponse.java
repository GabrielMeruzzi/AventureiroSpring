package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eClasse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AventureiroResumoResponse {
    private Long id;
    private String nome;
    private eClasse classe;
    private int nivel;
    private boolean ativo;
}