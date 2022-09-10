package io.github.vendas.rest.service;

import io.github.vendas.domain.entities.Produto;
import io.github.vendas.domain.repository.ProdutoRepo;
import io.github.vendas.exceptions.ProdutoNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepo produtoRepo;

    public ProdutoService(ProdutoRepo produtoRepo) {
        this.produtoRepo = produtoRepo;
    }

    public List<Produto> findAllProdutos() {
        return  produtoRepo.findAll();
    }

    public Produto findProdutoById(Long id) {
        return produtoRepo.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto de id: " + id + ", não encontrado!"));
    }

    public Produto saveProduto(Produto produto) {
        return produtoRepo.save(produto);
    }

    public void updateProduto (Long id, Produto produto) {
        produtoRepo.findById(id)
                .ifPresentOrElse(p -> {
                            produto.setId(id);
                            produtoRepo.save(produto);
                        },
                        () -> new ProdutoNotFoundException("Produto de id: " + id + ", não encontrado!"));
    }

    public void deleteProduto(Long id) {
        produtoRepo.findById(id)
                .ifPresentOrElse(produto -> produtoRepo.delete(produto),
                        () -> new ProdutoNotFoundException("Produto de id: " + id + ", não encontrado!"));
    }

    public List<Produto> findProdutoByFilter (Produto filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return produtoRepo.findAll(example);
    }

}
