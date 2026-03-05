package com.example.AventureiroSpring.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Companheiro {
    private String nome;
    private eEspecie especie;
    private int lealdade;

    public Companheiro(String nome, eEspecie especie, int lealdade) {
        if (nome == null) throw new IllegalArgumentException("Nome obrigatório.");
        if (especie == null) throw new IllegalArgumentException("Espécie obrigatória.");
        if (lealdade < 0 || lealdade > 100) throw new IllegalArgumentException("Lealdade inválida.");

        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }
}
