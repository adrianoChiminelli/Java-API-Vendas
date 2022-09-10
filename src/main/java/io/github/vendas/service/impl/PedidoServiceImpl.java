package io.github.vendas.service.impl;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.domain.entities.ItemPedido;
import io.github.vendas.domain.entities.Pedido;
import io.github.vendas.domain.entities.Produto;
import io.github.vendas.domain.repository.ClienteRepo;
import io.github.vendas.domain.repository.ItemPedidoRepo;
import io.github.vendas.domain.repository.PedidoRepo;
import io.github.vendas.domain.repository.ProdutoRepo;
import io.github.vendas.exceptions.ClienteNotFoundException;
import io.github.vendas.exceptions.ProdutoNotFoundException;
import io.github.vendas.rest.dto.ItemPedidoDTO;
import io.github.vendas.rest.dto.PedidoDTO;
import io.github.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepo pedidoRepo;
    private final ProdutoRepo produtoRepo;
    private final ClienteRepo clienteRepo;
    private final ItemPedidoRepo itemPedidoRepo;

    @Transactional
    @Override
    public Pedido savePedido(PedidoDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new ClienteNotFoundException(""));

        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setValorTotal(dto.getValorTotal());

        Set<ItemPedido> itens = converteLista(dto.getItens(), novoPedido);

        pedidoRepo.save(novoPedido);
        itemPedidoRepo.saveAll(itens);
        novoPedido.setItensPedido(itens);

        return novoPedido;
    }

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoRepo.findAll();
    }

    private Set<ItemPedido> converteLista (Set<ItemPedidoDTO> itensPedido, Pedido pedido) {
        return itensPedido
                .stream()
                .map(item -> {
                    Produto produto = produtoRepo.findById(item.getIdProduto())
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto de id: " + item.getIdProduto() + ", não encontrado."));
                    return new ItemPedido(pedido, produto, item.getQuantidade());
                })
                .collect(Collectors.toSet());
    }

}
