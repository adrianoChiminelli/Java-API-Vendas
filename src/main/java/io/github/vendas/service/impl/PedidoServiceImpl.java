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
import io.github.vendas.exceptions.PedidoNotFoundException;
import io.github.vendas.exceptions.ProdutoNotFoundException;
import io.github.vendas.rest.dto.ItemPedidoDTO;
import io.github.vendas.rest.dto.PedidoDTO;
import io.github.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        Set<ItemPedido> itens = converteLista(dto.getItens(), novoPedido);

        novoPedido.setValorTotal(calcularValorTotal(itens));
        pedidoRepo.save(novoPedido);
        itemPedidoRepo.saveAll(itens);
        novoPedido.setItensPedido(itens);

        return novoPedido;
    }

    @Override
    public List<Pedido> getAllPedidos() {
        return pedidoRepo.findAll();
    }

    @Transactional
    @Override
    public void deletePedido(Long id) {
        pedidoRepo.findById(id)
                .ifPresentOrElse(pedido -> {
                    itemPedidoRepo.deleteAll(pedido.getItensPedido());
                    pedidoRepo.deleteById(id);
                }, () -> new PedidoNotFoundException("pedido de id: " + id + ", não encontrado!"));
    }

    @Override
    public Set<ItemPedido> findItemPedidoByPedidoId(Long id) {
        return itemPedidoRepo.FindByPedido(id);
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

    private BigDecimal calcularValorTotal (Set<ItemPedido> itemPedidos){

        return BigDecimal.valueOf(
                itemPedidos.stream()
                        .mapToDouble(item -> {
                            Integer quantidade = item.getQuantidade();
                            Double preco = item.getProduto()
                                    .getPreco().doubleValue();
                            return quantidade * preco;
                        })
                        .sum()
        ).setScale(2);
    }

}
