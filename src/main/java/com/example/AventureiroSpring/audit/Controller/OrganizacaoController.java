package com.example.AventureiroSpring.audit.Controller;

import com.example.AventureiroSpring.audit.model.Organizacao;
import com.example.AventureiroSpring.audit.service.OrganizacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/organizacoes")
public class OrganizacaoController {
    private final OrganizacaoService organizacaoService;

    public OrganizacaoController(OrganizacaoService organizacaoService) {
        this.organizacaoService = organizacaoService;
    }

    @PostMapping
    public Organizacao criar(@RequestBody Organizacao organizacao) {
        return organizacaoService.criar(organizacao);
    }

    @GetMapping
    public List<Organizacao> listarTodas() {
        return organizacaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Organizacao> buscarPorId(@PathVariable Long id) {
        return organizacaoService.buscarPorId(id);
    }

    @GetMapping("/nome/{nome}")
    public Optional<Organizacao> buscarPorNome(@PathVariable String nome) {
        return organizacaoService.buscarPorNome(nome);
    }

    @PutMapping("/{id}")
    public Organizacao atualizar(@PathVariable Long id, @RequestBody Organizacao organizacao) {
        return organizacaoService.atualizar(id, organizacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        organizacaoService.deletar(id);
    }
}