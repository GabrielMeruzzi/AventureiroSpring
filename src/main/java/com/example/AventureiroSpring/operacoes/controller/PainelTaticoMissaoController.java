package com.example.AventureiroSpring.operacoes.controller;

import com.example.AventureiroSpring.operacoes.model.PainelTaticoMissao;
import com.example.AventureiroSpring.operacoes.service.PainelTaticoMissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paineltaticomissao")
public class PainelTaticoMissaoController {
    private final PainelTaticoMissaoService painelTaticoMissaoService;

    public PainelTaticoMissaoController(PainelTaticoMissaoService painelTaticoMissaoService) {
        this.painelTaticoMissaoService = painelTaticoMissaoService;
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> getTop15Dias() {
        List<PainelTaticoMissao> missoes = painelTaticoMissaoService.buscarTop10Missoes15Dias();
        return ResponseEntity.ok(missoes);
    }
}
