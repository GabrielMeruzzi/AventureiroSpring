package com.example.AventureiroSpring.aventura.repository;

import com.example.AventureiroSpring.aventura.model.Aventureiro;
import com.example.AventureiroSpring.aventura.model.eClasse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    @Query("SELECT a FROM Aventureiro a WHERE " +
            "(:ativo IS NULL OR a.ativo = :ativo) AND " +
            "(:classe IS NULL OR a.classe = :classe) AND " +
            "(:nivelMin IS NULL OR a.nivel >= :nivelMin)")
    Page<Aventureiro> filtrarAventureiros(
            @Param("ativo") Boolean ativo,
            @Param("classe") eClasse classe,
            @Param("nivelMin") Integer nivelMin,
            Pageable pageable);

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}