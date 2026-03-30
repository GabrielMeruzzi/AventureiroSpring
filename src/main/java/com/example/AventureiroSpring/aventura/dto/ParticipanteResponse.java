package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.ePapelNaMissao;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipanteResponse {
    private Long aventureiroId;
    private String nomeAventureiro;
    private ePapelNaMissao papel;
    private double recompensa;
    private boolean mvp;
}