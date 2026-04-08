package com.example.AventureiroSpring.operacoes.service;

import com.example.AventureiroSpring.operacoes.model.PainelTaticoMissao;
import com.example.AventureiroSpring.operacoes.repository.PainelTaticoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository repository;

    @Cacheable(value = "top-missoes")
    public List<PainelTaticoMissao> buscarTop10Missoes15Dias() {
        LocalDateTime dataLimite = LocalDateTime.now().minusDays(15);
        return repository
                .findByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataLimite)
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }
}
