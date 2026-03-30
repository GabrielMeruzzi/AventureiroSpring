package com.example.AventureiroSpring.aventura.Controller;

import com.example.AventureiroSpring.aventura.model.Companheiro;
import com.example.AventureiroSpring.aventura.service.CompanheiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companheiros")
@RequiredArgsConstructor
public class CompanheiroController {

    private final CompanheiroService companheiroService;

    @GetMapping
    public List<Companheiro> listarTodos() {
        return companheiroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Companheiro buscarPorId(@PathVariable Long id) {
        return companheiroService.buscarPorId(id);
    }
}