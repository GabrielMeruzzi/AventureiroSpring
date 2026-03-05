package com.example.AventureiroSpring.dto;

import com.example.AventureiroSpring.model.eClasse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarAventureiroRequest {
    private String nome;
    private eClasse classe;
    private int nivel;
}
