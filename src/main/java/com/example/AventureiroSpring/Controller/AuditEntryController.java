package com.example.AventureiroSpring.Controller;

import com.example.AventureiroSpring.model.AuditEntry;
import com.example.AventureiroSpring.service.AuditEntryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/audit-entries")
public class AuditEntryController {

    private final AuditEntryService auditEntryService;

    public AuditEntryController(AuditEntryService auditEntryService) {
        this.auditEntryService = auditEntryService;
    }

    @PostMapping
    public AuditEntry registrar(@RequestBody AuditEntry auditEntry) {
        return auditEntryService.registrar(auditEntry);
    }

    @GetMapping
    public Page<AuditEntry> listarTodos(@PageableDefault(size = 20, sort = "occurredAt") Pageable pageable) {
        return auditEntryService.listarTodos(pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}")
    public Page<AuditEntry> listarPorOrganizacao(
            @PathVariable Long organizacaoId,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable) {
        return auditEntryService.listarPorOrganizacao(organizacaoId, pageable);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Page<AuditEntry> listarPorUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable) {
        return auditEntryService.listarPorUsuario(usuarioId, pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}/action/{action}")
    public Page<AuditEntry> listarPorOrganizacaoEAction(
            @PathVariable Long organizacaoId,
            @PathVariable String action,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable) {
        return auditEntryService.listarPorOrganizacaoEAction(organizacaoId, action, pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}/entidade/{entityName}")
    public Page<AuditEntry> listarPorOrganizacaoEEntidade(
            @PathVariable Long organizacaoId,
            @PathVariable String entityName,
            @PageableDefault(size = 20, sort = "occurredAt") Pageable pageable) {
        return auditEntryService.listarPorOrganizacaoEEntidade(organizacaoId, entityName, pageable);
    }

    @GetMapping("/{id}")
    public Optional<AuditEntry> buscarPorId(@PathVariable Long id) {
        return auditEntryService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        auditEntryService.deletar(id);
    }
}