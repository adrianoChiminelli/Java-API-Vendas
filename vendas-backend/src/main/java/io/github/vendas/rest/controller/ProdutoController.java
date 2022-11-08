package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Produto;
import io.github.vendas.service.impl.ProdutoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoServiceImpl;

    @GetMapping
    public List<Produto> findAllProdutos() {
        return produtoServiceImpl.findAllProdutos();
    }

    @GetMapping("/{id}")
    public Produto findProdutoById(@PathVariable Long id) {
        return produtoServiceImpl.findProdutoById(id);
    }

    @GetMapping("/buscar")
    public List<Produto> findProdutoByFilter(Produto filter) {
        return produtoServiceImpl.findProdutoByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveNewProduto (@RequestBody @Valid Produto produto) {
        return produtoServiceImpl.saveProduto(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable Long id){
        produtoServiceImpl.deleteProduto(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        produtoServiceImpl.updateProduto(id, produto);
    }

}
