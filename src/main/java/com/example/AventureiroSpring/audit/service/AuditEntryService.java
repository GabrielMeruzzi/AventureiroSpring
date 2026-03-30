package com.example.AventureiroSpring.audit.service;

import com.example.AventureiroSpring.audit.model.AuditEntry;
import com.example.AventureiroSpring.audit.repository.AuditEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditEntryService {
    private final AuditEntryRepository auditEntryRepository;

    public AuditEntryService(AuditEntryRepository auditEntryRepository) {
        this.auditEntryRepository = auditEntryRepository;
    }

    public AuditEntry registrar(AuditEntry auditEntry) {
        return auditEntryRepository.save(auditEntry);
    }

    public Page<AuditEntry> listarTodos(Pageable pageable) {
        return auditEntryRepository.findAll(pageable);
    }

    public Page<AuditEntry> listarPorOrganizacao(Long organizacaoId, Pageable pageable) {
        return auditEntryRepository.findByOrganizacaoId(organizacaoId, pageable);
    }

    public Page<AuditEntry> listarPorUsuario(Long usuarioId, Pageable pageable) {
        return auditEntryRepository.findByUsuarioId(usuarioId, pageable);
    }

    public Page<AuditEntry> listarPorOrganizacaoEAction(Long organizacaoId, String action, Pageable pageable) {
        return auditEntryRepository.findByOrganizacaoIdAndAction(organizacaoId, action, pageable);
    }

    public Page<AuditEntry> listarPorOrganizacaoEEntidade(Long organizacaoId, String entityName, Pageable pageable) {
        return auditEntryRepository.findByOrganizacaoIdAndEntityName(organizacaoId, entityName, pageable);
    }

    public Optional<AuditEntry> buscarPorId(Long id) {
        return auditEntryRepository.findById(id);
    }

    public void deletar(Long id) {
        auditEntryRepository.deleteById(id);
    }
}