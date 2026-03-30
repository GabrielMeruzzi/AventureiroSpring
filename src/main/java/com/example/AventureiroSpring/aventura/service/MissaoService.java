package com.example.AventureiroSpring.aventura.service;

import com.example.AventureiroSpring.aventura.dto.*;
import com.example.AventureiroSpring.aventura.model.*;
import com.example.AventureiroSpring.aventura.repository.AventureiroRepository;
import com.example.AventureiroSpring.aventura.repository.MissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissaoService {

    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;

    public Page<MissaoResumoResponse> listarComFiltros(eStatusMissao status, eNivelPerigo perigo,
                                                       LocalDate inicio, LocalDate fim, Pageable pageable) {
        return missaoRepository.filtrarMissoes(status, perigo, inicio, fim, pageable)
                .map(m -> MissaoResumoResponse.builder()
                        .id(m.getId())
                        .titulo(m.getTitulo())
                        .status(m.getStatusMissao())
                        .nivelPerigo(m.getNivelPerigo())
                        .dataCriacao(m.getDataCriacao())
                        .dataInicio(m.getDataInicio())
                        .dataTermino(m.getDataTermino())
                        .build());
    }

    public MissaoDetalheResponse buscarDetalhada(Long id) {
        Missao missao = missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada."));

        List<ParticipanteResponse> participantes = missao.getParticipantes().stream()
                .map(p -> ParticipanteResponse.builder()
                        .aventureiroId(p.getAventureiro().getId())
                        .nomeAventureiro(p.getAventureiro().getNome())
                        .papel(p.getPapelNaMissao())
                        .recompensa(p.getRecompensaEmOuro())
                        .mvp(p.isMvp())
                        .build())
                .collect(Collectors.toList());

        return MissaoDetalheResponse.builder()
                .id(missao.getId())
                .titulo(missao.getTitulo())
                .nivelPerigo(missao.getNivelPerigo())
                .status(missao.getStatusMissao())
                .dataCriacao(missao.getDataCriacao())
                .dataInicio(missao.getDataInicio())
                .dataTermino(missao.getDataTermino())
                .participantes(participantes)
                .build();
    }

    public Missao buscarPorId(Long id) {
        return missaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada."));
    }

    public Missao criar(Missao missao) {
        validarMissao(missao);
        missao.setId(null);
        if (missao.getDataCriacao() == null) missao.setDataCriacao(LocalDate.now());
        return missaoRepository.save(missao);
    }

    public Missao atualizar(Long id, Missao dados) {
        Missao missao = buscarPorId(id);
        validarMissao(dados);
        missao.setTitulo(dados.getTitulo());
        missao.setNivelPerigo(dados.getNivelPerigo());
        missao.setStatusMissao(dados.getStatusMissao());
        missao.setDataInicio(dados.getDataInicio());
        missao.setDataTermino(dados.getDataTermino());
        missao.setOrganizacao(dados.getOrganizacao());
        return missaoRepository.save(missao);
    }

    public void deletar(Long id) {
        Missao missao = buscarPorId(id);
        missaoRepository.delete(missao);
    }

    public Missao adicionarAventureiro(Long missaoId, Long aventureiroId, ePapelNaMissao papel, double ouro, boolean mvp) {
        Missao missao = buscarPorId(missaoId);
        Aventureiro aventureiro = aventureiroRepository.findById(aventureiroId)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));
        missao.adicionarAventureiro(aventureiro, papel, ouro, mvp);
        return missaoRepository.save(missao);
    }

    public Missao removerAventureiro(Long missaoId, Long aventureiroId) {
        Missao missao = buscarPorId(missaoId);
        missao.getParticipantes().removeIf(reg -> reg.getAventureiro().getId().equals(aventureiroId));
        return missaoRepository.save(missao);
    }

    private void validarMissao(Missao missao) {
        if (missao.getTitulo() == null || missao.getTitulo().isBlank())
            throw new IllegalArgumentException("Título é obrigatório.");
        if (missao.getNivelPerigo() == null)
            throw new IllegalArgumentException("Nível de perigo é obrigatório.");
        if (missao.getStatusMissao() == null)
            throw new IllegalArgumentException("Status é obrigatório.");
        if (missao.getOrganizacao() == null || missao.getOrganizacao().getId() == null)
            throw new IllegalArgumentException("Organização é obrigatória.");
    }
}