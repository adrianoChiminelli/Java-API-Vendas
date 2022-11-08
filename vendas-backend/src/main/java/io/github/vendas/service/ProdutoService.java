package io.github.vendas.service;

import io.github.vendas.domain.entities.Produto;

import java.util.List;

public interface ProdutoService {
    List<Produto> findAllProdutos();

    Produto findProdutoById(Long id);

    Produto saveProduto(Produto produto);

    void updateProduto(Long id, Produto produto);

    void deleteProduto(Long id);

    List<Produto> findProdutoByFilter(Produto filter);
}
