package com.example.AventureiroSpring.aventura.repository;

import com.example.AventureiroSpring.aventura.dto.RankingAventureiroResponse;
import com.example.AventureiroSpring.aventura.model.RegistroDeMissao;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistroDeMissaoRepository extends JpaRepository<RegistroDeMissao, Long> {

    List<RegistroDeMissao> findByMissaoId(Long missaoId);

    List<RegistroDeMissao> findByAventureiroId(Long aventureiroId);

    long countByAventureiroId(Long aventureiroId);

    @Query("SELECT r FROM RegistroDeMissao r WHERE r.aventureiro.id = :id ORDER BY r.dataRegistro DESC, r.id DESC LIMIT 1")
    Optional<RegistroDeMissao> findUltimaMissaoPorAventureiro(@Param("id") Long aventureiroId);

    @Query("SELECT new com.example.AventureiroSpring.aventura.dto.RankingAventureiroResponse(" +
            "a.id, a.nome, COUNT(r), SUM(r.recompensaEmOuro), COUNT(CASE WHEN r.mvp = true THEN 1 END)) " +
            "FROM RegistroDeMissao r JOIN r.aventureiro a JOIN r.missao m " +
            "WHERE (:inicio IS NULL OR r.dataRegistro >= :inicio) " +
            "AND (:fim IS NULL OR r.dataRegistro <= :fim) " +
            "AND (:status IS NULL OR m.statusMissao = :status) " +
            "GROUP BY a.id, a.nome " +
            "ORDER BY COUNT(r) DESC, SUM(r.recompensaEmOuro) DESC")
    List<RankingAventureiroResponse> obterRankingAventureiros(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("status") eStatusMissao status);
}