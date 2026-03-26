package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
    Optional<Organizacao> findByNome(String nome);
}
