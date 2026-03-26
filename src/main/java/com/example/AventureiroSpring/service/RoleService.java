package com.example.AventureiroSpring.service;

import com.example.AventureiroSpring.model.Role;
import com.example.AventureiroSpring.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role criar(Role role) {
        return roleRepository.save(role);
    }

    public Page<Role> listarTodas(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Page<Role> listarPorOrganizacao(Long organizacaoId, Pageable pageable) {
        return roleRepository.findByOrganizacaoId(organizacaoId, pageable);
    }

    public Optional<Role> buscarPorId(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> buscarPorNome(String nome) {
        return roleRepository.findByNome(nome);
    }

    public Optional<Role> buscarPorOrganizacaoENome(Long organizacaoId, String nome) {
        return roleRepository.findByOrganizacaoIdAndNome(organizacaoId, nome);
    }

    public Role atualizar(Long id, Role dadosNovos) {
        return roleRepository.findById(id).map(role -> {
            role.setNome(dadosNovos.getNome());
            role.setDescricao(dadosNovos.getDescricao());
            role.setPermissions(dadosNovos.getPermissions());
            return roleRepository.save(role);
        }).orElseThrow(() -> new RuntimeException("Role não encontrada: " + id));
    }

    public void deletar(Long id) {
        roleRepository.deleteById(id);
    }
}