package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.ItemPedido;
import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.rest.dto.PedidoDTO;
import io.github.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id);
    }

    @GetMapping("/{id}/itens")
    public Set<ItemPedido> getAllItemPedido(@PathVariable Long id) {
        return pedidoService.findItemPedidoByPedidoId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido savePedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.savePedido(pedidoDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        pedidoService.updatePedido(id, pedidoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
    }
}
