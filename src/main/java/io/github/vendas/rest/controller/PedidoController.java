package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.rest.dto.PedidoDTO;
import io.github.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido savePedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.savePedido(pedidoDTO);
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }
}
