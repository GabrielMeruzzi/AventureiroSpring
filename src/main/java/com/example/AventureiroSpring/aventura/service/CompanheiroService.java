package com.example.AventureiroSpring.aventura.service;

import com.example.AventureiroSpring.aventura.model.Companheiro;
import com.example.AventureiroSpring.aventura.repository.CompanheiroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanheiroService {
    private final CompanheiroRepository companheiroRepository;

    public List<Companheiro> listarTodos() {
        return companheiroRepository.findAll();
    }

    public Companheiro buscarPorId(Long id) {
        return companheiroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Companheiro não encontrado."));
    }
}