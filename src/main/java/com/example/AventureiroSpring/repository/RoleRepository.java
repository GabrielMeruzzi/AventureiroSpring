package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
    Optional<Role> findByNomeAndDescricao(String nome, String descricao);
    Page<Role> findByOrganizacaoId(Long organizacaoId, Pageable pageable);
    Optional<Role> findByOrganizacaoIdAndNome(Long organizacaoId, String nome);
}
