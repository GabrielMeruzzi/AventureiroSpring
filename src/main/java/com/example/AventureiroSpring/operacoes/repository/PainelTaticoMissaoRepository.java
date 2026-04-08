package com.example.AventureiroSpring.operacoes.repository;

import com.example.AventureiroSpring.operacoes.model.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {
    List<PainelTaticoMissao> findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(LocalDateTime dataLimite);
}
