package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eNivelPerigo;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class MissaoResumoResponse {
    private Long id;
    private String titulo;
    private eStatusMissao status;
    private eNivelPerigo nivelPerigo;
    private LocalDate dataCriacao;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
}