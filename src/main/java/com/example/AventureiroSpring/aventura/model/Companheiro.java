package com.example.AventureiroSpring.aventura.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "companheiros", schema = "aventura")
public class Companheiro {
    @Id
    @Column(name = "aventureiro_id")
    private Long id;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(
            name = "aventureiro_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_companheiro_aventureiro")
    )
    private Aventureiro aventureiro;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private eEspecie especie;

    @Column(nullable = false)
    private int lealdade;

    public Companheiro(Aventureiro aventureiro, String nome, eEspecie especie, int lealdade) {
        if (lealdade < 0 || lealdade > 100) throw new IllegalArgumentException("Lealdade deve ser entre 0 e 100.");
        this.aventureiro = aventureiro;
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }
}
