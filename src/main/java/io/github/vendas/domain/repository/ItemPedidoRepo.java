package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.ItemPedido;
import io.github.vendas.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepo extends JpaRepository<ItemPedido, Long> {

    //List<ItemPedido> FindByPedido(Pedido pedido);
}
