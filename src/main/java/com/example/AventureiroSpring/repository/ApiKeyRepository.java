package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.ApiKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Page<ApiKey> findByOrganizacaoId(Long organizacaoId, Pageable pageable);
    Optional<ApiKey> findByOrganizacaoIdAndNome(Long organizacaoId, String nome);
    Optional<ApiKey> findByKeyHash(String keyHash);
}
