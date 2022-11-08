package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepo extends JpaRepository<Produto, Long> {
}
