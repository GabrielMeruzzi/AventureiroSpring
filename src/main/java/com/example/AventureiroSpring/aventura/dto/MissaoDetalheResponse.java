package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eNivelPerigo;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class MissaoDetalheResponse {
    private Long id;
    private String titulo;
    private eNivelPerigo nivelPerigo;
    private eStatusMissao status;
    private LocalDate dataCriacao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private List<ParticipanteResponse> participantes;
}