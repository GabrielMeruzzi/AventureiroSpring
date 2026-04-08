package com.example.AventureiroSpring.elastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "guilda_loja")
public class ProdutoDocument {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private BigDecimal preco;
}