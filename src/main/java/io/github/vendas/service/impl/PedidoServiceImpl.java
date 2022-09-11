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
import io.github.vendas.exceptions.ProdutoOutOfStockException;
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
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado!"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());

        Set<ItemPedido> itens = converteLista(dto.getItens(), pedido);

        pedido.setValorTotal(calculaValorTotal(itens));

        pedidoRepo.save(pedido);
        itemPedidoRepo.saveAll(itens);
        pedido.setItensPedido(itens);

        return pedido;
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

    @Override
    public Pedido getPedidoById(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("pedido de id: " + id + ", não encontrado!"));
    }

    @Override
    public void updatePedido(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("pedido de id: " + id + ", não encontrado!"));

        Cliente cliente = clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado!"));

        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        pedidoRepo.save(pedido);
    }

    @Transactional
    private Set<ItemPedido> converteLista (Set<ItemPedidoDTO> itensPedido, Pedido pedido) {
        return itensPedido
                .stream()
                .map(item -> {
                    Produto produto = produtoRepo.findById(item.getIdProduto())
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto de id: " + item.getIdProduto() + ", não encontrado."));

                    Integer quantidadeEstoque = produto.getQuantidadeEstoque();
                    Integer quantidadePedido = item.getQuantidade();

                    if(quantidadePedido > quantidadeEstoque) {
                        throw new ProdutoOutOfStockException("Quantidade do pedido execede a quantidade em estoque." +
                                " Quantidade em estoque: " + quantidadeEstoque + ".");
                    } else {
                        produto.setQuantidadeEstoque(quantidadeEstoque - quantidadePedido);
                        produtoRepo.save(produto);
                        return new ItemPedido(pedido, produto, quantidadePedido);
                    }
                })
                .collect(Collectors.toSet());
    }

    private BigDecimal calculaValorTotal(Set<ItemPedido> itemPedidos){

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
