package io.github.vendas.service;

import io.github.vendas.domain.entities.ItemPedido;
import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.rest.dto.ItemPedidoDTO;
import io.github.vendas.rest.dto.PedidoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface PedidoService {

    Pedido savePedido(PedidoDTO dto);

    List<Pedido> getAllPedidos();

    @Transactional
    void deletePedido(Long id);

    Set<ItemPedido> findItemPedidoByPedidoId(Long id);

    Pedido getPedidoById(Long id);

    void updatePedido(Long id, PedidoDTO dto);

}
