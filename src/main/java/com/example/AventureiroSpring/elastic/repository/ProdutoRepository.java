package com.example.AventureiroSpring.elastic.repository;

import com.example.AventureiroSpring.elastic.model.ProdutoDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProdutoRepository extends ElasticsearchRepository<ProdutoDocument, String> {
}
