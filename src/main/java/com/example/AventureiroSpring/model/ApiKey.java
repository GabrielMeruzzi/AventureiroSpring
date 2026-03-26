package com.example.AventureiroSpring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "api_keys", schema = "audit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_apiKey_org_nome", columnNames = {"organizacao_id", "nome"})
        })
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "organizacao_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_api_key_organizacao")
    )
    private Organizacao organizacao;
    @Column(nullable = false)
    private String nome;
    @Column(name = "key_hash", nullable = false)
    private String keyHash;
    @Column(nullable = false)
    private Boolean ativo;
    @Column(name = "createdAt", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;
}
