package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Produto;
import io.github.vendas.service.impl.ProdutoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoServiceImpl;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping
    public List<Produto> findAllProdutos() {
        return produtoServiceImpl.findAllProdutos();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Produto findProdutoById(@PathVariable Long id) {
        return produtoServiceImpl.findProdutoById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/buscar")
    public List<Produto> findProdutoByFilter(Produto filter) {
        return produtoServiceImpl.findProdutoByFilter(filter);
    }

    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveNewProduto (@RequestBody @Valid Produto produto) {
        return produtoServiceImpl.saveProduto(produto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable Long id){
        produtoServiceImpl.deleteProduto(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        produtoServiceImpl.updateProduto(id, produto);
    }

}
