package com.example.AventureiroSpring.aventura.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RankingAventureiroResponse {
    private Long aventureiroId;
    private String nomeAventureiro;
    private long totalParticipacoes;
    private double somaRecompensas;
    private long totalDestaquesMvp;
}