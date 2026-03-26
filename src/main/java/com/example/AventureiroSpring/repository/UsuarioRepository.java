package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByOrganizacaoId(Long organizacaoId, Pageable pageable);
    Optional<Usuario> findByOrganizacaoIdAndEmail(Long organizacaoId, String email);
}
