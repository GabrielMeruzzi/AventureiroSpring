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
@Table(name = "roles", schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_org_nome", columnNames = {"organizacao_id", "nome"})
        })
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_role_organizacao")
    )
    private Organizacao organizacao;
    @Column(nullable = false)
    private String nome;
    private String descricao;
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Builder.Default
    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            schema = "audit",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();
}