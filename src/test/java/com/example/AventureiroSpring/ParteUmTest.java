package com.example.AventureiroSpring;

import com.example.AventureiroSpring.model.*;
import com.example.AventureiroSpring.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParteUmTest {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    OrganizacaoRepository organizacaoRepository;
    @Autowired
    ApiKeyRepository apiKeyRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    public OffsetDateTime agora = OffsetDateTime.now();
    public Organizacao organizacao;

    @BeforeEach
    void setup() {
        organizacao = new Organizacao();
        organizacao.setNome("Organizacao Test");
        organizacao.setAtivo(true);
        organizacao.setCreatedAt(agora);
        organizacaoRepository.save(organizacao);
    }

    @Test
    void loadUsers() {
        Usuario usuario = new Usuario();
        usuario.setOrganizacao(organizacao);
        usuario.setNome("gabriel");
        usuario.setEmail("gabriel@gmail.com");
        usuario.setSenhaHash("teste");
        usuario.setStatus(eStatus.ATIVO);
        usuario.setCreatedAt(agora);
        usuario.setUpdatedAt(agora);
        usuario.setUltimoLoginEm(agora);
        usuarioRepository.save(usuario);

        Optional<Usuario> userTeste = usuarioRepository.findByEmail("gabriel@gmail.com");
        assertEquals("gabriel@gmail.com", userTeste.get().getEmail());
    }

    @Test
    void loadRoles() {
        Role role = new Role();
        role.setNome("ADMIN");
        role.setDescricao("Administrador");
        role.setCreatedAt(agora);
        role.setOrganizacao(organizacao);
        roleRepository.save(role);

        Optional<Role> roleTeste = roleRepository.findByNomeAndDescricao("ADMIN", "Administrador");
        assertEquals("ADMIN", roleTeste.get().getNome());
    }

    @Test
    void OrganizacaoRelationship() {
        Usuario usuario = new Usuario();
        usuario.setOrganizacao(organizacao);
        usuario.setNome("gabriel");
        usuario.setEmail("gabriel@gmail.com");
        usuario.setSenhaHash("teste");
        usuario.setStatus(eStatus.ATIVO);
        usuario.setCreatedAt(agora);
        usuario.setUpdatedAt(agora);
        usuario.setUltimoLoginEm(agora);

        ApiKey apiKey = new ApiKey();
        apiKey.setOrganizacao(organizacao);
        apiKey.setNome("API TESTE");
        apiKey.setKeyHash("254035435");
        apiKey.setAtivo(true);
        apiKey.setCreatedAt(agora);

        usuarioRepository.save(usuario);
        apiKeyRepository.save(apiKey);

        Usuario usuarioSalvo = usuarioRepository.findByEmail("gabriel@gmail.com").orElseThrow();

        assertEquals("Organizacao Test", usuarioSalvo.getOrganizacao().getNome());
        assertEquals(organizacao.getId(), usuarioSalvo.getOrganizacao().getId());

        ApiKey apiKeySalva = apiKeyRepository.findById(apiKey.getId()).orElseThrow();

        assertEquals("Organizacao Test", apiKeySalva.getOrganizacao().getNome());
        assertEquals(organizacao.getId(), apiKeySalva.getOrganizacao().getId());
    }

    @Test
    void acessarPermissoesViaRoles() {
        Permission p1 = new Permission();
        p1.setCode("USER_READ");
        p1.setDescricao("Permite ler usuários");

        Permission p2 = new Permission();
        p2.setCode("USER_WRITE");
        p2.setDescricao("Permite editar usuários");

        permissionRepository.save(p1);
        permissionRepository.save(p2);

        Role role = new Role();
        role.setNome("ADMIN");
        role.setDescricao("Administrador");
        role.setCreatedAt(agora);
        role.setOrganizacao(organizacao);

        role.getPermissions().add(p1);
        role.getPermissions().add(p2);

        roleRepository.save(role);

        Role roleSalva = roleRepository.findById(role.getId()).orElseThrow();

        assertThat(roleSalva.getPermissions()).hasSize(2);
        assertThat(roleSalva.getPermissions())
                .extracting(Permission::getCode)
                .containsExactlyInAnyOrder("USER_READ", "USER_WRITE");
    }
}
