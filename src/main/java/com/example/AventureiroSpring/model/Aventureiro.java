package com.example.AventureiroSpring.model;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Aventureiro {
    private long id;
    private String nome;
    private eClasse classe;
    private int nivel;
    private boolean ativo;
    private Companheiro companheiro;

    public Aventureiro( long id, String nome, eClasse classe, int nivel) {
        if (id < 0)
            throw new IllegalArgumentException("ID não pode ser menor que 0.");
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (classe == null)
            throw new IllegalArgumentException("Classe não pode ser nulo.");
        if (nivel < 1)
            throw new IllegalArgumentException("Nível deve ser maior ou igual a 1.");
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
    }

    public Aventureiro(long id, String nome, eClasse classe, int nivel, Companheiro companheiro) {
        this(id, nome, classe, nivel);
        this.companheiro = companheiro;
    }

    public void adicionarCompanheiro(String nome, eEspecie especie, int lealdade) {
        if (this.companheiro != null)
            throw new IllegalStateException("Este aventureiro já possui um companheiro.");
        this.companheiro = new Companheiro(nome, especie, lealdade);
    }
}
