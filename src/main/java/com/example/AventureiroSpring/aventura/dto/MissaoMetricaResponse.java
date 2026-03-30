package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eNivelPerigo;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MissaoMetricaResponse {
    private Long missaoId;
    private String titulo;
    private eStatusMissao status;
    private eNivelPerigo nivelPerigo;
    private long quantidadeParticipantes;
    private double recompensaTotal;
}