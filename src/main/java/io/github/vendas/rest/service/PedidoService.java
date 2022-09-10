package io.github.vendas.rest.service;

import io.github.vendas.domain.repository.PedidoRepo;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepo pedidoRepo;

    public PedidoService(PedidoRepo pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }



}
