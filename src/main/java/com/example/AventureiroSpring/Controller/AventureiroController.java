package com.example.AventureiroSpring.Controller;

import com.example.AventureiroSpring.dto.AtualizarAventureiroRequest;
import com.example.AventureiroSpring.dto.CriarAventureiroRequest;
import com.example.AventureiroSpring.dto.CriarCompanheiroRequest;
import com.example.AventureiroSpring.dto.PagedResponse;
import com.example.AventureiroSpring.model.Aventureiro;
import com.example.AventureiroSpring.model.eClasse;
import com.example.AventureiroSpring.service.AventureiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {
    @Autowired
    private AventureiroService service;

    @PostMapping
    public ResponseEntity<Aventureiro> criar(@RequestBody CriarAventureiroRequest request) {
        Aventureiro novo = service.registrarAventureiro(
                request.getNome(),
                request.getClasse(),
                request.getNivel()
        );
        return ResponseEntity.status(201).body(novo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aventureiro> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.listarAventureiroPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Aventureiro>> listar(
            @RequestParam(required = false) eClasse classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        PagedResponse<Aventureiro> response =
                service.listarAventureiros(classe, ativo, nivelMin, page, size);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", String.valueOf(response.getTotal()))
                .header("X-Page", String.valueOf(response.getPage()))
                .header("X-Size", String.valueOf(response.getSize()))
                .header("X-Total-Pages", String.valueOf(response.getTotalPages()))
                .body(response.getAventureiros());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aventureiro> atualizar(
            @PathVariable long id,
            @RequestBody AtualizarAventureiroRequest request
    ) {
        Aventureiro atualizado = service.atualizarDadosAventureiro(
                id,
                request.getNome(),
                request.getClasse(),
                request.getNivel()
        );

        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Void> encerrar(@PathVariable long id) {
        service.encerrarVinculoAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<Void> renovar(@PathVariable long id) {
        service.renovarVinculoAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> adicionarCompanheiro(
            @PathVariable long id,
            @RequestBody CriarCompanheiroRequest request
    ) {
        Aventureiro a = service.adicionarCompanheiro(
                id,
                request.getNome(),
                request.getEspecie(),
                request.getLealdade()
        );

        return ResponseEntity.ok(a);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Aventureiro> removerCompanheiro(@PathVariable long id) {
        return ResponseEntity.ok(service.removerCompanheiro(id));
    }
}