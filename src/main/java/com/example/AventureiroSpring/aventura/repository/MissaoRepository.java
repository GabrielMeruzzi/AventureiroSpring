package com.example.AventureiroSpring.aventura.repository;

import com.example.AventureiroSpring.aventura.dto.MissaoMetricaResponse;
import com.example.AventureiroSpring.aventura.model.Missao;
import com.example.AventureiroSpring.aventura.model.eNivelPerigo;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query("SELECT m FROM Missao m WHERE " +
            "(:status IS NULL OR m.statusMissao = :status) AND " +
            "(:perigo IS NULL OR m.nivelPerigo = :perigo) AND " +
            "(:dataInicio IS NULL OR m.dataCriacao >= :dataInicio) AND " +
            "(:dataFim IS NULL OR m.dataCriacao <= :dataFim)")
    Page<Missao> filtrarMissoes(
            @Param("status") eStatusMissao status,
            @Param("perigo") eNivelPerigo perigo,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable);

    @Query("SELECT new com.example.AventureiroSpring.aventura.dto.MissaoMetricaResponse(" +
            "m.id, m.titulo, m.statusMissao, m.nivelPerigo, COUNT(p), COALESCE(SUM(p.recompensaEmOuro), 0.0)) " +
            "FROM Missao m LEFT JOIN m.participantes p " +
            "WHERE (:inicio IS NULL OR m.dataCriacao >= :inicio) " +
            "AND (:fim IS NULL OR m.dataCriacao <= :fim) " +
            "GROUP BY m.id, m.titulo, m.statusMissao, m.nivelPerigo")
    List<MissaoMetricaResponse> obterMetricasMissoes(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);
}