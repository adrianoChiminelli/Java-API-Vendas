package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepo extends JpaRepository<Pedido, Long> {

    //List<Pedido> findByCliente(Cliente cliente);
}
