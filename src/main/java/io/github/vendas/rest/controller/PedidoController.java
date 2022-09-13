package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.ItemPedido;
import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.rest.dto.ItemPedidoDTO;
import io.github.vendas.rest.dto.PedidoDTO;
import io.github.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/{id}/itens")
    public Set<ItemPedido> getAllItemPedido(@PathVariable Long id) {
        return pedidoService.findItemPedidoByPedidoId(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido savePedido(@RequestBody @Valid PedidoDTO pedidoDTO) {
        return pedidoService.savePedido(pedidoDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDTO) {
        pedidoService.updatePedido(id, pedidoDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
    }
}
