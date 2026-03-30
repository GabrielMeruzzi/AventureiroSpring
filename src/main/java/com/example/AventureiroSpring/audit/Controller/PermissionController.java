package com.example.AventureiroSpring.audit.Controller;

import com.example.AventureiroSpring.audit.model.Permission;
import com.example.AventureiroSpring.audit.service.PermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public Permission criar(@RequestBody Permission permission) {
        return permissionService.criar(permission);
    }

    @GetMapping
    public Page<Permission> listarTodas(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return permissionService.listarTodas(pageable);
    }

    @GetMapping("/buscar")
    public Page<Permission> buscarPorCode(
            @RequestParam String code,
            @PageableDefault(size = 20, sort = "code") Pageable pageable) {
        return permissionService.buscarPorCode(code, pageable);
    }

    @GetMapping("/{id}")
    public Optional<Permission> buscarPorId(@PathVariable Long id) {
        return permissionService.buscarPorId(id);
    }

    @GetMapping("/code/{code}")
    public Optional<Permission> buscarPorCode(@PathVariable String code) {
        return permissionService.buscarPorCodeExato(code);
    }

    @PutMapping("/{id}")
    public Permission atualizar(@PathVariable Long id, @RequestBody Permission permission) {
        return permissionService.atualizar(id, permission);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        permissionService.deletar(id);
    }
}