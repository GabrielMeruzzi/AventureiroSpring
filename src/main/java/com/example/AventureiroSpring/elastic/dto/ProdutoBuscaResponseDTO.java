package com.example.AventureiroSpring.elastic.dto;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.AventureiroSpring.elastic.model.ProdutoDocument;

import java.math.BigDecimal;

public record ProdutoBuscaResponseDTO(
        String nome,
        String descricao,
        String categoria,
        String raridade,
        BigDecimal preco)
{

    public static ProdutoBuscaResponseDTO toDto(Hit<ProdutoDocument> hit) {
        ProdutoDocument doc = hit.source();
        return new ProdutoBuscaResponseDTO(
                doc.getNome(),
                doc.getDescricao(),
                doc.getCategoria(),
                doc.getRaridade(),
                doc.getPreco()
        );
    }
}