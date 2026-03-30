package com.example.AventureiroSpring.aventura.service;

import com.example.AventureiroSpring.aventura.dto.*;
import com.example.AventureiroSpring.aventura.model.*;
import com.example.AventureiroSpring.aventura.repository.AventureiroRepository;
import com.example.AventureiroSpring.aventura.repository.RegistroDeMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AventureiroService {

    private final AventureiroRepository aventureiroRepository;
    private final RegistroDeMissaoRepository registroDeMissaoRepository;

    public List<Aventureiro> listarTodos() {
        return aventureiroRepository.findAll();
    }

    public Aventureiro buscarPorId(Long id) {
        return aventureiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));
    }

    public AventureiroDetalheResponse buscarDetalhes(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        long totalParticipacoes = registroDeMissaoRepository.countByAventureiroId(id);

        UltimaMissaoResponse ultimaMissaoDto = registroDeMissaoRepository.findUltimaMissaoPorAventureiro(id)
                .map(reg -> UltimaMissaoResponse.builder()
                        .id(reg.getMissao().getId())
                        .titulo(reg.getMissao().getTitulo())
                        .nivelPerigo(reg.getMissao().getNivelPerigo())
                        .statusMissao(reg.getMissao().getStatusMissao())
                        .dataRegistro(reg.getDataRegistro())
                        .build())
                .orElse(null);

        CompanheiroResumoResponse companheiroDto = null;
        if (aventureiro.getCompanheiro() != null) {
            companheiroDto = CompanheiroResumoResponse.builder()
                    .id(aventureiro.getCompanheiro().getId())
                    .nome(aventureiro.getCompanheiro().getNome())
                    .especie(aventureiro.getCompanheiro().getEspecie())
                    .lealdade(aventureiro.getCompanheiro().getLealdade())
                    .build();
        }

        return AventureiroDetalheResponse.builder()
                .id(aventureiro.getId())
                .nome(aventureiro.getNome())
                .classe(aventureiro.getClasse())
                .nivel(aventureiro.getNivel())
                .ativo(aventureiro.isAtivo())
                .companheiro(companheiroDto)
                .totalParticipacoes(totalParticipacoes)
                .ultimaMissao(ultimaMissaoDto)
                .build();
    }

    public Aventureiro criar(Aventureiro aventureiro) {
        validarAventureiro(aventureiro);
        aventureiro.setId(null);
        aventureiro.setDataCriacao(LocalDateTime.now());
        aventureiro.setUltimaAlteracao(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro atualizar(Long id, Aventureiro dados) {
        Aventureiro aventureiro = buscarPorId(id);
        validarAventureiro(dados);
        aventureiro.setNome(dados.getNome());
        aventureiro.setClasse(dados.getClasse());
        aventureiro.setNivel(dados.getNivel());
        aventureiro.setAtivo(dados.isAtivo());
        aventureiro.setOrganizacao(dados.getOrganizacao());
        aventureiro.setUsuario(dados.getUsuario());
        aventureiro.setUltimaAlteracao(LocalDateTime.now());
        return aventureiroRepository.save(aventureiro);
    }

    public void deletar(Long id) {
        Aventureiro aventureiro = buscarPorId(id);
        aventureiroRepository.delete(aventureiro);
    }

    public Companheiro adicionarCompanheiro(Long aventureiroId, String nome, eEspecie especie, int lealdade) {
        Aventureiro aventureiro = buscarPorId(aventureiroId);
        aventureiro.adicionarCompanheiro(nome, especie, lealdade);
        aventureiro.setUltimaAlteracao(LocalDateTime.now());
        aventureiroRepository.save(aventureiro);
        return aventureiro.getCompanheiro();
    }

    public Aventureiro removerCompanheiro(Long aventureiroId) {
        Aventureiro aventureiro = buscarPorId(aventureiroId);
        aventureiro.removerCompanheiro();
        aventureiro.setUltimaAlteracao(LocalDateTime.now());
        aventureiroRepository.save(aventureiro);
        return aventureiro;
    }

    public Page<AventureiroResumoResponse> listarComFiltros(Boolean ativo, eClasse classe, Integer nivelMin, Pageable pageable) {
        return aventureiroRepository.filtrarAventureiros(ativo, classe, nivelMin, pageable)
                .map(this::mapToResumo);
    }

    public Page<AventureiroResumoResponse> buscarPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(this::mapToResumo);
    }

    private AventureiroResumoResponse mapToResumo(Aventureiro a) {
        return AventureiroResumoResponse.builder()
                .id(a.getId())
                .nome(a.getNome())
                .classe(a.getClasse())
                .nivel(a.getNivel())
                .ativo(a.isAtivo())
                .build();
    }

    private void validarAventureiro(Aventureiro aventureiro) {
        if (aventureiro.getNome() == null || aventureiro.getNome().isBlank())
            throw new IllegalArgumentException("Nome do aventureiro é obrigatório.");
        if (aventureiro.getClasse() == null)
            throw new IllegalArgumentException("Classe do aventureiro é obrigatória.");
        if (aventureiro.getNivel() < 1)
            throw new IllegalArgumentException("Nível do aventureiro deve ser no mínimo 1.");
        if (aventureiro.getOrganizacao() == null || aventureiro.getOrganizacao().getId() == null)
            throw new IllegalArgumentException("Organização é obrigatória.");
        if (aventureiro.getUsuario() == null || aventureiro.getUsuario().getId() == null)
            throw new IllegalArgumentException("Usuário é obrigatório.");
    }
}