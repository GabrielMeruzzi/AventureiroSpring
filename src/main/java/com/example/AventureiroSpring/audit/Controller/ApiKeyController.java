package com.example.AventureiroSpring.audit.Controller;

import com.example.AventureiroSpring.audit.model.ApiKey;
import com.example.AventureiroSpring.audit.service.ApiKeyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api-keys")
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping
    public ApiKey criar(@RequestBody ApiKey apiKey) {
        return apiKeyService.criar(apiKey);
    }

    @GetMapping
    public Page<ApiKey> listarTodas(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return apiKeyService.listarTodas(pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}")
    public Page<ApiKey> listarPorOrganizacao(
            @PathVariable Long organizacaoId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return apiKeyService.listarPorOrganizacao(organizacaoId, pageable);
    }

    @GetMapping("/{id}")
    public Optional<ApiKey> buscarPorId(@PathVariable Long id) {
        return apiKeyService.buscarPorId(id);
    }

    @GetMapping("/organizacao/{organizacaoId}/nome/{nome}")
    public Optional<ApiKey> buscarPorOrganizacaoENome(
            @PathVariable Long organizacaoId,
            @PathVariable String nome) {
        return apiKeyService.buscarPorOrganizacaoENome(organizacaoId, nome);
    }

    @PutMapping("/{id}")
    public ApiKey atualizar(@PathVariable Long id, @RequestBody ApiKey apiKey) {
        return apiKeyService.atualizar(id, apiKey);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        apiKeyService.deletar(id);
    }
}