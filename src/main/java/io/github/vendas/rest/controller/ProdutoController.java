package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Produto;
import io.github.vendas.exceptions.ProdutoNotFoundException;
import io.github.vendas.rest.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> findAllProdutos() {
        return produtoService.findAllProdutos();
    }

    @GetMapping("/{id}")
    public Produto findProdutoById(@PathVariable Long id) {
        return produtoService.findProdutoById(id);
    }

    @GetMapping("/buscar")
    public List<Produto> findProdutoByFilter(Produto filter) {
        return produtoService.findProdutoByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveNewProduto (@RequestBody Produto produto) {
        return produtoService.saveProduto(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable Long id){
        produtoService.deleteProduto(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        produtoService.updateProduto(id, produto);
    }

}
