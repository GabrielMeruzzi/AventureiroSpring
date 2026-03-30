package com.example.AventureiroSpring.aventura.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(
        name = "registro_de_missoes",
        schema = "aventura",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_registro_missao_aventureiro",
                        columnNames = {"missao_id", "aventureiro_id"}
                )
        }
)
public class RegistroDeMissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "missao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_registroMissao_missao")
    )
    private Missao missao;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "aventureiro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_registroMissao_aventureiro")
    )
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_na_missao", nullable = false)
    private ePapelNaMissao papelNaMissao;

    @Column(name = "recompensa_em_ouro")
    private double recompensaEmOuro;

    @Column(nullable = false)
    private boolean mvp;

    @Column(name = "data_registro", nullable = false)
    @Builder.Default
    private LocalDate dataRegistro = LocalDate.now();
}