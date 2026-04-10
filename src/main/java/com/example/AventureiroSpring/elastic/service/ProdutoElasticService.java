package com.example.AventureiroSpring.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.AventureiroSpring.elastic.dto.DocumentCountDTO;
import com.example.AventureiroSpring.elastic.dto.FaixaPrecoResponseDTO;
import com.example.AventureiroSpring.elastic.dto.PrecoMedioResponseDTO;
import com.example.AventureiroSpring.elastic.dto.ProdutoBuscaResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProdutoElasticService {
    private final ElasticsearchClient client;

    public List<ProdutoBuscaResponseDTO> buscarPorNome(String termo) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(termo)
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarPorDescricao(String termo) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("descricao")
                                .query(termo)
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarPorFrase(String termo) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .matchPhrase(mp -> mp
                                .field("descricao")
                                .query(termo)
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarPorNomeFuzzy(String termo) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(termo)
                                .fuzziness("AUTO")
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarPorNomeDescricao(String termo) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .multiMatch(m -> m
                                .query(termo)
                                .fields("nome", "descricao")

                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarComFiltro(String termo, String categoria) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .bool(b -> b
                                .must(m -> m
                                        .match(mt -> mt
                                                .field("descricao")
                                                .query(termo)
                                        )
                                )
                                .filter(f -> f
                                        .term(t -> t
                                                .field("categoria")
                                                .value(categoria)
                                        )
                                )
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscarPorFaixaPreco(Double min, Double max) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .range(r -> r
                                                .number(n -> n
                                                        .field("preco")
                                                        .gte(min)
                                                        .lte(max)
                                                )
                                        )
                                )
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<ProdutoBuscaResponseDTO> buscaFiltroAvancado(String categoria, String raridade, double min, double max) throws IOException {
        SearchResponse<ProdutoBuscaResponseDTO> response = client.search(s -> s
                .index("guilda_loja")
                .query(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .term(t -> t
                                                .field("categoria")
                                                .value(categoria)
                                        )
                                )
                                .filter(f -> f
                                        .term(t -> t
                                                .field("raridade")
                                                .value(raridade)
                                        )
                                )
                                .filter(f -> f
                                        .range(r -> r
                                                .number(n -> n
                                                        .field("preco")
                                                        .gte(min)
                                                        .lte(max)
                                                )
                                        )
                                )
                        )
                ), ProdutoBuscaResponseDTO.class
        );

        return response.hits().hits()
                .stream()
                .map(Hit::source)
                .toList();
    }

    public List<DocumentCountDTO> quantidadeProdutosPorCategoria() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("agg_categoria", a -> a
                                .terms(t -> t.field("categoria"))
                        ),
                Void.class
        );

        return response.aggregations()
                .get("agg_categoria")
                .sterms()
                .buckets()
                .array()
                .stream()
                .map(bucket -> new DocumentCountDTO(
                        bucket.key().stringValue(),
                        bucket.docCount()
                ))
                .toList();
    }

    public List<DocumentCountDTO> quantidadeProdutosPorRaridade() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("agg_raridade", a -> a
                                .terms(t -> t.field("categoria"))
                        ),
                Void.class
        );

        return response.aggregations()
                .get("agg_raridade")
                .sterms()
                .buckets()
                .array()
                .stream()
                .map(bucket -> new DocumentCountDTO(
                        bucket.key().stringValue(),
                        bucket.docCount()
                ))
                .toList();
    }

    public PrecoMedioResponseDTO precoMedioProdutos() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("preco_medio", a -> a
                                .avg(avg -> avg.field("preco"))
                        ),
                Void.class
        );

        Double precoMedio = response.aggregations()
                .get("preco_medio")
                .avg()
                .value();

        return new PrecoMedioResponseDTO(precoMedio);
    }

    public List<FaixaPrecoResponseDTO> contarPorFaixaPreco() throws IOException {
        SearchResponse<Void> response = client.search(s -> s
                        .index("guilda_loja")
                        .size(0)
                        .aggregations("faixa_preco", a -> a
                                .range(r -> r
                                        .field("preco")
                                        .ranges(
                                                AggregationRange.of(rg -> rg.to(100.0)),
                                                AggregationRange.of(rg -> rg.from(100.0).to(300.0)),
                                                AggregationRange.of(rg -> rg.from(300.0).to(700.0)),
                                                AggregationRange.of(rg -> rg.from(700.0))
                                        )
                                )
                        ),
                Void.class
        );

        var buckets = response.aggregations()
                .get("faixa_preco")
                .range()
                .buckets()
                .array();

        return List.of(
                new FaixaPrecoResponseDTO("Abaixo de 100", buckets.get(0).docCount()),
                new FaixaPrecoResponseDTO("100 a 300", buckets.get(1).docCount()),
                new FaixaPrecoResponseDTO("300 a 700", buckets.get(2).docCount()),
                new FaixaPrecoResponseDTO("Acima de 700", buckets.get(3).docCount())
        );
    }
}