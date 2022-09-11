package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ItemPedidoRepo extends JpaRepository<ItemPedido, Long> {

    @Query(value = "select i from ItemPedido i where pedido_id = :id")
    Set<ItemPedido> FindByPedido( @Param("id") Long idPedido);
}
