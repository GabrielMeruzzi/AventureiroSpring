package com.example.AventureiroSpring;

import com.example.AventureiroSpring.aventura.dto.MissaoMetricaResponse;
import com.example.AventureiroSpring.aventura.dto.RankingAventureiroResponse;
import com.example.AventureiroSpring.aventura.model.Aventureiro;
import com.example.AventureiroSpring.aventura.model.RegistroDeMissao;
import com.example.AventureiroSpring.aventura.model.eClasse;
import com.example.AventureiroSpring.aventura.model.eStatusMissao;
import com.example.AventureiroSpring.aventura.repository.AventureiroRepository;
import com.example.AventureiroSpring.aventura.repository.MissaoRepository;
import com.example.AventureiroSpring.aventura.repository.RegistroDeMissaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParteTresTest {

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private RegistroDeMissaoRepository registroDeMissaoRepository;


    @Test
    @DisplayName("Deve validar listagem com filtro")
    void deveListarAventureirosComFiltro() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nivel").descending());

        Page<Aventureiro> resultado = aventureiroRepository.filtrarAventureiros(
                true,
                eClasse.GUERREIRO,
                40,
                pageable
        );

        assertThat(resultado).isNotNull();
        assertThat(resultado.getContent()).isNotEmpty();

        List<String> nomes = resultado.getContent().stream()
                .map(Aventureiro::getNome)
                .toList();

        assertThat(nomes).contains("Aragorn", "Gimli");

        assertThat(resultado.getContent())
                .allMatch(a -> a.getClasse() == eClasse.GUERREIRO)
                .allMatch(a -> a.getNivel() >= 40)
                .allMatch(Aventureiro::isAtivo);
    }


    @Test
    @DisplayName("Deve validar consulta detalhada")
    void deveConsultarDetalhesDoAventureiro() {
        Aventureiro aventureiro = aventureiroRepository.findByNomeContainingIgnoreCase(
                "Aragorn",
                PageRequest.of(0, 1)
        ).getContent().get(0);

        assertThat(aventureiro.getId()).isEqualTo(1L);
        assertThat(aventureiro.getNome()).isEqualTo("Aragorn");
        assertThat(aventureiro.getClasse().name()).isEqualTo("GUERREIRO");
        assertThat(aventureiro.getNivel()).isEqualTo(50);
        assertThat(aventureiro.getCompanheiro()).isNotNull();
        assertThat(aventureiro.getCompanheiro().getNome()).isEqualTo("Brego");
    }


    @Test
    @DisplayName("Deve validar relatório agregado")
    void deveGerarRelatorioAgregadoDeMissoes() {
        List<MissaoMetricaResponse> resultado = missaoRepository.obterMetricasMissoes(null, null);

        assertThat(resultado).isNotEmpty();

        assertThat(resultado)
                .extracting(MissaoMetricaResponse::getTitulo)
                .contains("Destruir o Anel", "Minas de Moria", "Coletar Ervas");

        MissaoMetricaResponse destruirAnel = resultado.stream()
                .filter(m -> m.getTitulo().equals("Destruir o Anel"))
                .findFirst()
                .orElseThrow();

        assertThat(destruirAnel.getQuantidadeParticipantes()).isEqualTo(3);
        assertThat(destruirAnel.getRecompensaTotal()).isEqualTo(16000.0);
    }


    @Test
    @DisplayName("Deve validar ranking de aventureiros")
    void deveGerarRankingDeAventureiros() {
        List<RankingAventureiroResponse> ranking =
                registroDeMissaoRepository.obterRankingAventureiros(null, null, null);

        assertThat(ranking).isNotEmpty();

        RankingAventureiroResponse primeiro = ranking.get(0);

        assertThat(primeiro.getNomeAventureiro()).isEqualTo("Aragorn");
        assertThat(primeiro.getTotalParticipacoes()).isEqualTo(1);
        assertThat(primeiro.getSomaRecompensas()).isEqualTo(10000.0);
        assertThat(primeiro.getTotalDestaquesMvp()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve validar consulta com múltiplos relacionamentos")
    void deveConsultarComMultiplosRelacionamentos() {
        List<RegistroDeMissao> registros = registroDeMissaoRepository.findByMissaoId(1L);

        assertThat(registros).isNotNull();
        assertThat(registros).hasSize(3);

        RegistroDeMissao registro = registros.stream()
                .filter(r -> r.getAventureiro().getNome().equals("Aragorn"))
                .findFirst()
                .orElseThrow();

        assertThat(registro.getMissao()).isNotNull();
        assertThat(registro.getMissao().getTitulo()).isEqualTo("Destruir o Anel");

        assertThat(registro.getAventureiro()).isNotNull();
        assertThat(registro.getAventureiro().getNome()).isEqualTo("Aragorn");
        assertThat(registro.getAventureiro().getClasse().name()).isEqualTo("GUERREIRO");

        assertThat(registro.getAventureiro().getCompanheiro()).isNotNull();
        assertThat(registro.getAventureiro().getCompanheiro().getNome()).isEqualTo("Brego");

        assertThat(registro.getPapelNaMissao().name()).isEqualTo("ATAQUE");
        assertThat(registro.getRecompensaEmOuro()).isEqualTo(10000.0);
        assertThat(registro.isMvp()).isTrue();
    }
}