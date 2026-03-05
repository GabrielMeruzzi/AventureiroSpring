package com.example.AventureiroSpring.dto;

import com.example.AventureiroSpring.model.eEspecie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarCompanheiroRequest {
    private String nome;
    private eEspecie especie;
    private int lealdade;
}
