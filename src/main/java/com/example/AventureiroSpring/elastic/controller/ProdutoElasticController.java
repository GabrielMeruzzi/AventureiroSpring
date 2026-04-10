package com.example.AventureiroSpring.elastic.controller;

import com.example.AventureiroSpring.elastic.dto.DocumentCountDTO;
import com.example.AventureiroSpring.elastic.dto.FaixaPrecoResponseDTO;
import com.example.AventureiroSpring.elastic.dto.PrecoMedioResponseDTO;
import com.example.AventureiroSpring.elastic.dto.ProdutoBuscaResponseDTO;
import com.example.AventureiroSpring.elastic.service.ProdutoElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoElasticController {
    private final ProdutoElasticService produtoElasticService;

    @GetMapping("/busca/nome")
    public List<ProdutoBuscaResponseDTO> buscarPorNome(@RequestParam String termo) throws IOException {
        return produtoElasticService.buscarPorNome(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoBuscaResponseDTO> buscarPorDescricao(@RequestParam String termo) throws IOException {
        return produtoElasticService.buscarPorDescricao(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProdutoBuscaResponseDTO> buscarPorFrase(@RequestParam String termo) throws IOException {
        return produtoElasticService.buscarPorFrase(termo);
    }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoBuscaResponseDTO> buscarPorNomeFuzzy(@RequestParam String termo) throws IOException {
        return produtoElasticService.buscarPorNomeFuzzy(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoBuscaResponseDTO> buscarPorNomeDescricao(@RequestParam String termo) throws IOException {
        return produtoElasticService.buscarPorNomeDescricao(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoBuscaResponseDTO> buscarComFiltro(
            @RequestParam String termo,
            @RequestParam String categoria
    ) throws IOException {
        return produtoElasticService.buscarComFiltro(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoBuscaResponseDTO> buscarPorFaixaPreco(
            @RequestParam Double min,
            @RequestParam Double max
    ) throws IOException {
        return produtoElasticService.buscarPorFaixaPreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoBuscaResponseDTO> buscaFiltroAvancado(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max
    ) throws IOException {
        return produtoElasticService.buscaFiltroAvancado(categoria, raridade, min, max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public List<DocumentCountDTO> quantidadeProdutosPorCategoria() throws IOException {
        return produtoElasticService.quantidadeProdutosPorCategoria();
    }

    @GetMapping("/agregacoes/por-raridade")
    public List<DocumentCountDTO> quantidadeProdutosPorRaridade() throws IOException {
        return produtoElasticService.quantidadeProdutosPorRaridade();
    }

    @GetMapping("/agregacoes/preco-medio")
    public PrecoMedioResponseDTO precoMedioProdutos() throws IOException {
        return produtoElasticService.precoMedioProdutos();
    }

    @GetMapping("/agregacoes/faixas-preco")
    public List<FaixaPrecoResponseDTO> contarPorFaixaPreco() throws IOException {
        return produtoElasticService.contarPorFaixaPreco();
    }
}