package com.example.AventureiroSpring.Controller;

import com.example.AventureiroSpring.model.Usuario;
import com.example.AventureiroSpring.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {
        return usuarioService.criar(usuario);
    }

    @GetMapping
    public Page<Usuario> listarTodos(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return usuarioService.listarTodos(pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}")
    public Page<Usuario> listarPorOrganizacao(
            @PathVariable Long organizacaoId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return usuarioService.listarPorOrganizacao(organizacaoId, pageable);
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }

    @GetMapping("/organizacao/{organizacaoId}/email/{email}")
    public Optional<Usuario> buscarPorOrganizacaoEEmail(
            @PathVariable Long organizacaoId,
            @PathVariable String email) {
        return usuarioService.buscarPorOrganizacaoEEmail(organizacaoId, email);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}