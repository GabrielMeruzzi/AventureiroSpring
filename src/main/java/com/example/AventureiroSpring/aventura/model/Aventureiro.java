package com.example.AventureiroSpring.aventura.model;

import com.example.AventureiroSpring.audit.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "aventureiros", schema = "aventura")
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_aventureiro_organizacao")
    )
    private Organizacao organizacao;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_aventureiro_usuario")
    )
    private Usuario usuario;

    @Column(nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private eClasse classe;

    // FALTA: Valor mínimo permitido: 1
    @Column(nullable = false)
    private int nivel;

    @Column(nullable = false)
    private boolean ativo;

    @Column(name="data_criacao",nullable = false)
    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name="ultima_alteracao", nullable = false)
    private LocalDateTime ultimaAlteracao;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;

    public void adicionarCompanheiro(String nome, eEspecie especie, int lealdade) {
        if (this.companheiro != null)
            throw new IllegalStateException("Este aventureiro já possui um companheiro.");
        this.companheiro = new Companheiro(this, nome, especie, lealdade);
    }

    public void removerCompanheiro() {
        this.companheiro = null;
    }
}
