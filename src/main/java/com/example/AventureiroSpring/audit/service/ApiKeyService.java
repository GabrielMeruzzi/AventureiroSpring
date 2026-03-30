package com.example.AventureiroSpring.audit.service;

import com.example.AventureiroSpring.audit.model.ApiKey;
import com.example.AventureiroSpring.audit.repository.ApiKeyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public ApiKey criar(ApiKey apiKey) {
        return apiKeyRepository.save(apiKey);
    }

    public Page<ApiKey> listarTodas(Pageable pageable) {
        return apiKeyRepository.findAll(pageable);
    }

    public Page<ApiKey> listarPorOrganizacao(Long organizacaoId, Pageable pageable) {
        return apiKeyRepository.findByOrganizacaoId(organizacaoId, pageable);
    }

    public Optional<ApiKey> buscarPorId(Long id) {
        return apiKeyRepository.findById(id);
    }

    public Optional<ApiKey> buscarPorOrganizacaoENome(Long organizacaoId, String nome) {
        return apiKeyRepository.findByOrganizacaoIdAndNome(organizacaoId, nome);
    }

    public ApiKey atualizar(Long id, ApiKey dadosNovos) {
        return apiKeyRepository.findById(id).map(apiKey -> {
            apiKey.setNome(dadosNovos.getNome());
            apiKey.setAtivo(dadosNovos.getAtivo());
            apiKey.setKeyHash(dadosNovos.getKeyHash());
            return apiKeyRepository.save(apiKey);
        }).orElseThrow(() -> new RuntimeException("ApiKey não encontrada: " + id));
    }

    public void deletar(Long id) {
        apiKeyRepository.deleteById(id);
    }
}