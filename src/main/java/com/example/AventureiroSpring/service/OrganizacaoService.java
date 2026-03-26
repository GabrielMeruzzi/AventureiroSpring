package com.example.AventureiroSpring.service;

import com.example.AventureiroSpring.model.Organizacao;
import com.example.AventureiroSpring.repository.OrganizacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizacaoService {

    private final OrganizacaoRepository organizacaoRepository;

    public OrganizacaoService(OrganizacaoRepository organizacaoRepository) {
        this.organizacaoRepository = organizacaoRepository;
    }

    public Organizacao criar(Organizacao organizacao) {
        organizacao.setCreatedAt(OffsetDateTime.now());
        return organizacaoRepository.save(organizacao);
    }

    public List<Organizacao> listarTodas() {
        return organizacaoRepository.findAll();
    }

    public Optional<Organizacao> buscarPorId(Long id) {
        return organizacaoRepository.findById(id);
    }

    public Optional<Organizacao> buscarPorNome(String nome) {
        return organizacaoRepository.findByNome(nome);
    }

    public Organizacao atualizar(Long id, Organizacao dados) {
        Organizacao organizacao = organizacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organização não encontrada"));

        organizacao.setNome(dados.getNome());
        organizacao.setAtivo(dados.getAtivo());

        return organizacaoRepository.save(organizacao);
    }

    public void deletar(Long id) {
        organizacaoRepository.deleteById(id);
    }
}