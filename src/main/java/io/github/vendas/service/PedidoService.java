package io.github.vendas.service;

import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.rest.dto.PedidoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PedidoService {

    Pedido savePedido(PedidoDTO dto);

    List<Pedido> getAllPedidos();
}
