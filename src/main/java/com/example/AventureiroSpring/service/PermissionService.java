package com.example.AventureiroSpring.service;

import com.example.AventureiroSpring.model.Permission;
import com.example.AventureiroSpring.repository.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission criar(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Page<Permission> listarTodas(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    public Page<Permission> buscarPorCode(String code, Pageable pageable) {
        return permissionRepository.findByCodeContainingIgnoreCase(code, pageable);
    }

    public Optional<Permission> buscarPorId(Long id) {
        return permissionRepository.findById(id);
    }

    public Optional<Permission> buscarPorCodeExato(String code) {
        return permissionRepository.findByCode(code);
    }

    public Permission atualizar(Long id, Permission dadosNovos) {
        return permissionRepository.findById(id).map(permission -> {
            permission.setCode(dadosNovos.getCode());
            permission.setDescricao(dadosNovos.getDescricao());
            return permissionRepository.save(permission);
        }).orElseThrow(() -> new RuntimeException("Permission não encontrada: " + id));
    }

    public void deletar(Long id) {
        permissionRepository.deleteById(id);
    }
}