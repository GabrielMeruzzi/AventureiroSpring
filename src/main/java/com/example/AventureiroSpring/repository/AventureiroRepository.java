package com.example.AventureiroSpring.repository;

import com.example.AventureiroSpring.model.Aventureiro;
import com.example.AventureiroSpring.model.eClasse;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AventureiroRepository {
    private final List<Aventureiro> aventureiros = new ArrayList<>();
    private long idSequencial = 1;

    public Aventureiro salvar(Aventureiro aventureiro) {
        aventureiro.setId(idSequencial++);
        aventureiros.add(aventureiro);
        return aventureiro;
    }

    public Optional<Aventureiro> buscarPorId(long id) {
        return aventureiros.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    public List<Aventureiro> listarTodos() {
        return Collections.unmodifiableList(aventureiros);
    }

    @PostConstruct
    public void init() {
        int quantidade = 100;
        Random random = new Random();
        eClasse[] classes = eClasse.values();

        for (int i = 0; i < quantidade; i++) {
            eClasse classeAleatoria = classes[random.nextInt(classes.length)];
            int nivelAleatorio = random.nextInt(100) + 1;
            Aventureiro aventureiro = new Aventureiro(
                    0,
                    "Aventureiro " + i,
                    classeAleatoria,
                    nivelAleatorio
            );
            this.salvar(aventureiro);
        }
    }
}
