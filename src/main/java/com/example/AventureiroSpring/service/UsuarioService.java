package com.example.AventureiroSpring.service;

import com.example.AventureiroSpring.model.Usuario;
import com.example.AventureiroSpring.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Page<Usuario> listarPorOrganizacao(Long organizacaoId, Pageable pageable) {
        return usuarioRepository.findByOrganizacaoId(organizacaoId, pageable);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> buscarPorOrganizacaoEEmail(Long organizacaoId, String email) {
        return usuarioRepository.findByOrganizacaoIdAndEmail(organizacaoId, email);
    }

    public Usuario atualizar(Long id, Usuario dadosNovos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(dadosNovos.getNome());
            usuario.setEmail(dadosNovos.getEmail());
            usuario.setSenhaHash(dadosNovos.getSenhaHash());
            usuario.setStatus(dadosNovos.getStatus());
            usuario.setRoles(dadosNovos.getRoles());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}