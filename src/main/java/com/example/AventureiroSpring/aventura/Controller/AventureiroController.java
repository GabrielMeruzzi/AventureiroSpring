package com.example.AventureiroSpring.aventura.Controller;

import com.example.AventureiroSpring.aventura.dto.AventureiroDetalheResponse;
import com.example.AventureiroSpring.aventura.dto.AventureiroResumoResponse;
import com.example.AventureiroSpring.aventura.model.Aventureiro;
import com.example.AventureiroSpring.aventura.model.Companheiro;
import com.example.AventureiroSpring.aventura.model.eClasse;
import com.example.AventureiroSpring.aventura.model.eEspecie;
import com.example.AventureiroSpring.aventura.service.AventureiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventureiroService aventureiroService;

    @GetMapping
    public List<Aventureiro> listarTodos() {
        return aventureiroService.listarTodos();
    }

    @GetMapping("/{id}")
    public AventureiroDetalheResponse buscarPorId(@PathVariable Long id) {
        return aventureiroService.buscarDetalhes(id);
    }

    @PostMapping
    public Aventureiro criar(@RequestBody Aventureiro aventureiro) {
        return aventureiroService.criar(aventureiro);
    }

    @PutMapping("/{id}")
    public Aventureiro atualizar(@PathVariable Long id, @RequestBody Aventureiro aventureiro) {
        return aventureiroService.atualizar(id, aventureiro);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        aventureiroService.deletar(id);
    }

    @PostMapping("/{id}/companheiro")
    public Companheiro adicionarCompanheiro(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam eEspecie especie,
            @RequestParam int lealdade
    ) {
        return aventureiroService.adicionarCompanheiro(id, nome, especie, lealdade);
    }

    @DeleteMapping("/{id}/companheiro")
    public void removerCompanheiro(@PathVariable Long id) {
        aventureiroService.removerCompanheiro(id);
    }

    @GetMapping("/filtrar")
    public Page<AventureiroResumoResponse> listarComFiltros(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) eClasse classe,
            @RequestParam(required = false) Integer nivelMin,
            Pageable pageable) {
        return aventureiroService.listarComFiltros(ativo, classe, nivelMin, pageable);
    }

    @GetMapping("/busca")
    public Page<AventureiroResumoResponse> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        return aventureiroService.buscarPorNome(nome, pageable);
    }
}