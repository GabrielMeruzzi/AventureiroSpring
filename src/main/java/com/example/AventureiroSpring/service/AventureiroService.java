package com.example.AventureiroSpring.service;

import com.example.AventureiroSpring.Exception.BusinessException;
import com.example.AventureiroSpring.Exception.NotFoundException;
import com.example.AventureiroSpring.Exception.ValidationException;
import com.example.AventureiroSpring.dto.PagedResponse;
import com.example.AventureiroSpring.model.Aventureiro;
import com.example.AventureiroSpring.model.Companheiro;
import com.example.AventureiroSpring.model.eClasse;
import com.example.AventureiroSpring.model.eEspecie;
import com.example.AventureiroSpring.repository.AventureiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AventureiroService {
    @Autowired
    private AventureiroRepository repository;

    private Aventureiro buscarPorIdInterno(long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() ->
                        new NotFoundException("Aventureiro com id " + id + " não encontrado"));
    }

    public Aventureiro registrarAventureiro(String nome, eClasse classe, int nivel) {
        List<String> erros = new ArrayList<>();
        if (nome == null || nome.isBlank())
            erros.add("nome não pode ser vazio");
        if (classe == null)
            erros.add("classe inválida");
        if (nivel < 1)
            erros.add("nivel deve ser maior ou igual a 1");
        if (!erros.isEmpty())
            throw new ValidationException(erros);

        Aventureiro aventureiro = new Aventureiro(0, nome, classe, nivel);
        return repository.salvar(aventureiro);
    }

    public Aventureiro listarAventureiroPorId(long id) {
        return buscarPorIdInterno(id);
    }

    public PagedResponse<Aventureiro> listarAventureiros(
            eClasse classe,
            Boolean ativo,
            Integer nivelMin,
            int page,
            int size
    ) {

        if (page < 0)
            throw new ValidationException(List.of("page não pode ser negativo"));
        if (size < 1 || size > 50)
            throw new ValidationException(List.of("size deve estar entre 1 e 50"));

        List<Aventureiro> filtrados = repository.listarTodos()
                .stream()
                .filter(a -> classe == null || a.getClasse() == classe)
                .filter(a -> ativo == null || a.isAtivo() == ativo)
                .filter(a -> nivelMin == null || a.getNivel() >= nivelMin)
                .sorted(Comparator.comparingLong(Aventureiro::getId))
                .collect(Collectors.toList());

        int total = filtrados.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);

        List<Aventureiro> pagedList =
                fromIndex >= total ? List.of() : filtrados.subList(fromIndex, toIndex);

        return new PagedResponse<>(page, size, total, pagedList);
    }

    public Aventureiro atualizarDadosAventureiro(long id, String nome, eClasse classe, int nivel) {
        Aventureiro a = buscarPorIdInterno(id);
        if (nome != null && nome.isBlank())
            throw new ValidationException(List.of("nome não pode ser vazio"));
        if (nivel < 0)
            throw new ValidationException(List.of("nivel inválido"));
        if (nome != null)
            a.setNome(nome);
        if (classe != null)
            a.setClasse(classe);
        if (nivel > 0)
            a.setNivel(nivel);
        return a;
    }

    public void encerrarVinculoAventureiro(long id) {
        Aventureiro a = buscarPorIdInterno(id);
        if (!a.isAtivo())
            throw new BusinessException("Aventureiro já está inativo");
        a.setAtivo(false);
    }

    public void renovarVinculoAventureiro(long id) {
        Aventureiro a = buscarPorIdInterno(id);
        if (a.isAtivo())
            throw new BusinessException("Aventureiro já está ativo");
        a.setAtivo(true);
    }

    public Aventureiro adicionarCompanheiro(long idAventureiro, String nome, eEspecie especie, int lealdade) {
        Aventureiro a = buscarPorIdInterno(idAventureiro);
        List<String> erros = new ArrayList<>();

        if (nome == null || nome.isBlank())
            erros.add("nome do companheiro não pode ser vazio");
        if (especie == null)
            erros.add("espécie inválida");
        if (lealdade < 0)
            erros.add("lealdade não pode ser negativa");
        if (!erros.isEmpty())
            throw new ValidationException(erros);

        a.setCompanheiro(new Companheiro(nome, especie, lealdade));
        return a;
    }

    public Aventureiro removerCompanheiro(long idAventureiro) {
        Aventureiro a = buscarPorIdInterno(idAventureiro);
        a.setCompanheiro(null);
        return a;
    }
}