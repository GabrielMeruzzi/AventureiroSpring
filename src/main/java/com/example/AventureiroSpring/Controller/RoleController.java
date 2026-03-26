package com.example.AventureiroSpring.Controller;

import com.example.AventureiroSpring.model.Role;
import com.example.AventureiroSpring.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role criar(@RequestBody Role role) {
        return roleService.criar(role);
    }

    @GetMapping
    public Page<Role> listarTodas(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return roleService.listarTodas(pageable);
    }

    @GetMapping("/organizacao/{organizacaoId}")
    public Page<Role> listarPorOrganizacao(
            @PathVariable Long organizacaoId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return roleService.listarPorOrganizacao(organizacaoId, pageable);
    }

    @GetMapping("/{id}")
    public Optional<Role> buscarPorId(@PathVariable Long id) {
        return roleService.buscarPorId(id);
    }

    @GetMapping("/nome/{nome}")
    public Optional<Role> buscarPorNome(@PathVariable String nome) {
        return roleService.buscarPorNome(nome);
    }

    @GetMapping("/organizacao/{organizacaoId}/nome/{nome}")
    public Optional<Role> buscarPorOrganizacaoENome(
            @PathVariable Long organizacaoId,
            @PathVariable String nome) {
        return roleService.buscarPorOrganizacaoENome(organizacaoId, nome);
    }

    @PutMapping("/{id}")
    public Role atualizar(@PathVariable Long id, @RequestBody Role role) {
        return roleService.atualizar(id, role);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        roleService.deletar(id);
    }
}