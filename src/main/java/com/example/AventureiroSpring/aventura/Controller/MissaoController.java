package com.example.AventureiroSpring.aventura.Controller;

import com.example.AventureiroSpring.aventura.dto.MissaoDetalheResponse;
import com.example.AventureiroSpring.aventura.dto.MissaoResumoResponse;
import com.example.AventureiroSpring.aventura.model.*;
import com.example.AventureiroSpring.aventura.service.MissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/missoes")
@RequiredArgsConstructor
public class MissaoController {

    private final MissaoService missaoService;

    @GetMapping
    public Page<MissaoResumoResponse> listarComFiltros(
            @RequestParam(required = false) eStatusMissao status,
            @RequestParam(required = false) eNivelPerigo nivelPerigo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Pageable pageable) {
        return missaoService.listarComFiltros(status, nivelPerigo, dataInicio, dataFim, pageable);
    }

    @GetMapping("/{id}")
    public MissaoDetalheResponse buscarPorId(@PathVariable Long id) {
        return missaoService.buscarDetalhada(id);
    }

    @PostMapping
    public Missao criar(@RequestBody Missao missao) {
        return missaoService.criar(missao);
    }

    @PutMapping("/{id}")
    public Missao atualizar(@PathVariable Long id, @RequestBody Missao missao) {
        return missaoService.atualizar(id, missao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        missaoService.deletar(id);
    }

    @PostMapping("/{missaoId}/aventureiros/{aventureiroId}")
    public Missao adicionarAventureiro(
            @PathVariable Long missaoId,
            @PathVariable Long aventureiroId,
            @RequestParam ePapelNaMissao papelNaMissao,
            @RequestParam double recompensaEmOuro,
            @RequestParam boolean mvp
    ) {
        return missaoService.adicionarAventureiro(missaoId, aventureiroId, papelNaMissao, recompensaEmOuro, mvp);
    }

    @DeleteMapping("/{missaoId}/aventureiros/{aventureiroId}")
    public Missao removerAventureiro(
            @PathVariable Long missaoId,
            @PathVariable Long aventureiroId
    ) {
        return missaoService.removerAventureiro(missaoId, aventureiroId);
    }
}