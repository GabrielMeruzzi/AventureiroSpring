INSERT INTO audit.organizacoes (nome, ativo, created_at) VALUES
('Guilda dos Aventureiros', true, CURRENT_TIMESTAMP),
('Reino de Stormwind', true, CURRENT_TIMESTAMP);

INSERT INTO audit.permissions (code, descricao) VALUES
('MISS_CREATE', 'Permite criar novas missões'),
('MISS_DELETE', 'Permite deletar missões'),
('USER_BAN', 'Permite banir outros usuários'),
('VIEW_AUDIT', 'Permite ver logs de auditoria'),
('COMP_MANAGE', 'Permite gerenciar companheiros');

INSERT INTO audit.roles (organizacao_id, nome, descricao, created_at) VALUES
(1, 'MESTRE_DA_GUILDA', 'Acesso total à guilda', CURRENT_TIMESTAMP),
(1, 'AVENTUREIRO_ELITE', 'Acesso a missões de alto nível', CURRENT_TIMESTAMP),
(2, 'ADMIN_REINO', 'Administrador real', CURRENT_TIMESTAMP);

INSERT INTO audit.role_permissions (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 5);

INSERT INTO audit.usuarios (organizacao_id, nome, email, senha_hash, status, created_at, updated_at) VALUES
(1, 'Aragorn', 'passos@rangers.com', 'hash123', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Gandalf', 'cinzento@maiar.com', 'hash123', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 'Frodo', 'bolseiro@condado.com', 'hash123', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Gimli', 'filho_de_gloin@montanhas.com', 'hash123', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Legolas', 'folha_verde@trevas.com', 'hash123', 'ATIVO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO audit.user_roles (usuario_id, role_id) VALUES
(1, 1), (2, 1), (3, 2), (4, 3), (5, 3);

INSERT INTO audit.api_keys (organizacao_id, nome, key_hash, ativo, created_at) VALUES
(1, 'Key-Sistema-Externo', 'abc123hash', true, CURRENT_TIMESTAMP),
(2, 'Key-Monitoramento', 'xyz789hash', true, CURRENT_TIMESTAMP);

INSERT INTO audit.audit_entries (organizacao_id, actor_user_id, action, entity_schema, entity_name, entity_id, occurred_at, success, ip) VALUES
(1, 1, 'CREATE_USER', 'audit', 'usuarios', '3', CURRENT_TIMESTAMP, true, '127.0.0.1'),
(1, 2, 'LOGIN', 'audit', 'usuarios', '2', CURRENT_TIMESTAMP, true, '192.168.0.1');

INSERT INTO aventura.aventureiros (organizacao_id, usuario_id, nome, classe, nivel, ativo, data_criacao, ultima_alteracao) VALUES
(1, 1, 'Aragorn', 'GUERREIRO', 50, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'Gandalf', 'MAGO', 99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 3, 'Frodo', 'LADINO', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4, 'Gimli', 'GUERREIRO', 40, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5, 'Legolas', 'ARQUEIRO', 42, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO aventura.companheiros (aventureiro_id, nome, especie, lealdade) VALUES
(1, 'Brego', 'LOBO', 100), -- Simulação: Cavalo/Lobo
(2, 'Shadowfax', 'GOLEM', 100),
(4, 'Machado Velho', 'GOLEM', 90);

INSERT INTO aventura.missoes (organizacao_id, titulo, nivel_perigo, status_missao, data_criacao) VALUES
(1, 'Destruir o Anel', 'ALTO', 'EM_ANDAMENTO', CURRENT_DATE),
(1, 'Defender o Forte', 'ALTO', 'PLANEJADA', CURRENT_DATE),
(2, 'Minas de Moria', 'MEDIO', 'CONCLUIDA', CURRENT_DATE),
(1, 'Coletar Ervas', 'BAIXO', 'CONCLUIDA', CURRENT_DATE),
(2, 'Patrulha de Fronteira', 'BAIXO', 'PLANEJADA', CURRENT_DATE);

INSERT INTO aventura.registro_de_missoes (missao_id, aventureiro_id, papel_na_missao, recompensa_em_ouro, mvp, data_registro) VALUES
(1, 1, 'ATAQUE', 10000.0, true, CURRENT_DATE),
(1, 2, 'SUPORTE', 5000.0, false, CURRENT_DATE),
(1, 3, 'ATAQUE', 1000.0, false, CURRENT_DATE),
(3, 4, 'DEFESA', 500.0, true, CURRENT_DATE),
(3, 5, 'ATAQUE', 500.0, false, CURRENT_DATE);