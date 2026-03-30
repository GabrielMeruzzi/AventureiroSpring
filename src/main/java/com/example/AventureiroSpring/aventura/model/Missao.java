package com.example.AventureiroSpring.aventura.model;

import com.example.AventureiroSpring.audit.model.Organizacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "missoes", schema = "aventura")
public class Missao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_missao_organizacao")
    )
    private Organizacao organizacao;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RegistroDeMissao> participantes = new HashSet<>();

    @Column(nullable = false, length = 150)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_perigo", nullable = false)
    private eNivelPerigo nivelPerigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_missao", nullable = false)
    private eStatusMissao statusMissao;

    @Column(name = "data_criacao", nullable = false)
    @Builder.Default
    private LocalDate dataCriacao = LocalDate.now();

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    public void adicionarAventureiro(Aventureiro aventureiro, ePapelNaMissao papelNaMissao,
                                     double recompensaEmOuro, boolean mvp) {
        if (aventureiro == null)
            throw new IllegalArgumentException("Aventureiro não pode ser nulo.");

        if (papelNaMissao == null)
            throw new IllegalArgumentException("Papel na missão não pode ser nulo.");

        if (!this.organizacao.getId().equals(aventureiro.getOrganizacao().getId()))
            throw new IllegalArgumentException("Somente aventureiros da mesma organização podem participar da missão.");

        if (!aventureiro.isAtivo())
            throw new IllegalArgumentException("Apenas aventureiros ativos podem participar da missão.");

        if (this.statusMissao != eStatusMissao.PLANEJADA)
            throw new IllegalArgumentException("A missão não está em estado compatível para aceitar participantes.");

        boolean jaParticipa = this.participantes.stream()
                .anyMatch(registro -> registro.getAventureiro().getId().equals(aventureiro.getId()));

        if (jaParticipa)
            throw new IllegalArgumentException("Este aventureiro já está associado a esta missão.");

        RegistroDeMissao registro = new RegistroDeMissao();
        registro.setMissao(this);
        registro.setAventureiro(aventureiro);
        registro.setPapelNaMissao(papelNaMissao);
        registro.setRecompensaEmOuro(recompensaEmOuro);
        registro.setMvp(mvp);

        this.participantes.add(registro);
    }
}