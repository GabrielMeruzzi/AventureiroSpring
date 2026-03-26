package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.AuditEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
    Page<AuditEntry> findByOrganizacaoId(Long organizacaoId, Pageable pageable);
    Page<AuditEntry> findByUsuarioId(Long usuarioId, Pageable pageable);
    Page<AuditEntry> findByOrganizacaoIdAndAction(Long organizacaoId, String action, Pageable pageable);
    Page<AuditEntry> findByOrganizacaoIdAndEntityName(Long organizacaoId, String entity_name, Pageable pageable);
}
