package com.example.AventureiroSpring.aventura.dto;

import com.example.AventureiroSpring.aventura.model.eNivelPerigo;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UltimaMissaoResponse {
    private Long id;
    private String titulo;
    private eNivelPerigo nivelPerigo;
    private eStatusMissao statusMissao;
    private LocalDate dataRegistro;
}