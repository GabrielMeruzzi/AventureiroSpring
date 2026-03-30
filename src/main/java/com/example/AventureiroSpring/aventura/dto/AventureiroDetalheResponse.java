package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eClasse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AventureiroDetalheResponse {
    private Long id;
    private String nome;
    private eClasse classe;
    private int nivel;
    private boolean ativo;

    private CompanheiroResumoResponse companheiro;
    private long totalParticipacoes;
    private UltimaMissaoResponse ultimaMissao;
}