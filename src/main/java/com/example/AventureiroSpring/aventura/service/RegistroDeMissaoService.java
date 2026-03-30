package com.example.AventureiroSpring.aventura.service;

import com.example.AventureiroSpring.aventura.dto.MissaoMetricaResponse;
import com.example.AventureiroSpring.aventura.dto.RankingAventureiroResponse;
import com.example.AventureiroSpring.aventura.model.RegistroDeMissao;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import com.example.AventureiroSpring.aventura.repository.MissaoRepository;
import com.example.AventureiroSpring.aventura.repository.RegistroDeMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroDeMissaoService {

    private final RegistroDeMissaoRepository registroDeMissaoRepository;
    private final MissaoRepository missaoRepository;

    public List<RankingAventureiroResponse> obterRanking(LocalDate inicio, LocalDate fim, eStatusMissao status) {
        return registroDeMissaoRepository.obterRankingAventureiros(inicio, fim, status);
    }

    public List<MissaoMetricaResponse> obterMetricas(LocalDate inicio, LocalDate fim) {
        return missaoRepository.obterMetricasMissoes(inicio, fim);
    }

    public List<RegistroDeMissao> listarTodos() {
        return registroDeMissaoRepository.findAll();
    }

    public RegistroDeMissao buscarPorId(Long id) {
        return registroDeMissaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro não encontrado."));
    }

    public List<RegistroDeMissao> listarPorMissao(Long missaoId) {
        return registroDeMissaoRepository.findByMissaoId(missaoId);
    }

    public List<RegistroDeMissao> listarPorAventureiro(Long aventureiroId) {
        return registroDeMissaoRepository.findByAventureiroId(aventureiroId);
    }

    public void deletar(Long id) {
        registroDeMissaoRepository.deleteById(id);
    }
}