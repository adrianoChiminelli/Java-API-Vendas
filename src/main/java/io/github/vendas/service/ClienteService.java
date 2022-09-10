package io.github.vendas.service;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.exceptions.InvalidCPFException;

import java.util.List;

public interface ClienteService {

    Cliente SaveCliente(Cliente cliente) throws InvalidCPFException;

    List<Cliente> findAllClientes();

    Cliente findClienteById(Long id);

    void deleteCliente(Long id);

    void updateCliente(Long id, Cliente cliente) throws InvalidCPFException;

    List<Cliente> findClientesByFilter(Cliente filter);
}
