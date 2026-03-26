package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByCode(String code);
    Page<Permission> findByCodeContainingIgnoreCase(String code, Pageable pageable);
}
