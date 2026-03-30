package com.example.AventureiroSpring.aventura.Controller;

import com.example.AventureiroSpring.aventura.dto.MissaoMetricaResponse;
import com.example.AventureiroSpring.aventura.dto.RankingAventureiroResponse;
import com.example.AventureiroSpring.aventura.model.RegistroDeMissao;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import com.example.AventureiroSpring.aventura.service.RegistroDeMissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registros-missao")
@RequiredArgsConstructor
public class RegistroDeMissaoController {

    private final RegistroDeMissaoService registroDeMissaoService;

    @GetMapping("/ranking")
    public List<RankingAventureiroResponse> getRanking(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) eStatusMissao status) {
        return registroDeMissaoService.obterRanking(inicio, fim, status);
    }

    @GetMapping("/metricas-missoes")
    public List<MissaoMetricaResponse> getMetricas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return registroDeMissaoService.obterMetricas(inicio, fim);
    }

    @GetMapping
    public List<RegistroDeMissao> listarTodos() {
        return registroDeMissaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public RegistroDeMissao buscarPorId(@PathVariable Long id) {
        return registroDeMissaoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        registroDeMissaoService.deletar(id);
    }
}