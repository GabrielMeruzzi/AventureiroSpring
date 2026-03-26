package com.example.AventureiroSpring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuarios", schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_org_email", columnNames = {"organizacao_id", "email"})
        })
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_usuario_organizacao")
    )
    private Organizacao organizacao;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private eStatus status;
    @Column(name = "ultimo_login_em", nullable = false)
    private OffsetDateTime ultimoLoginEm;
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            schema = "audit",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
