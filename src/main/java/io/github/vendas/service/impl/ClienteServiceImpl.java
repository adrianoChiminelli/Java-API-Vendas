package io.github.vendas.service.impl;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.domain.repository.ClienteRepo;
import io.github.vendas.exceptions.ClienteNotFoundException;
import io.github.vendas.exceptions.CPFAlreadyExistsException;
import io.github.vendas.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepo clienteRepo;

    @Override
    public Cliente SaveCliente(Cliente cliente) {
        if (clienteRepo.existsByCpf(cliente.getCpf())) {
            throw new CPFAlreadyExistsException();
        } else {
            return clienteRepo.save(cliente);
        }
    }

    @Override
    public List<Cliente> findAllClientes(){
        return clienteRepo.findAll();
    }

    @Override
    public Cliente findClienteById(Long id){
        return clienteRepo.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente de id " + id + ", não encontrado!"));
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepo.findById(id)
                .ifPresentOrElse(cliente -> clienteRepo.deleteById(id),
                        () -> new ClienteNotFoundException("Cliente de id " + id + ", não encontrado!"));
    }

    @Override
    public void updateCliente(Long id, Cliente cliente) throws CPFAlreadyExistsException {
            clienteRepo.findById(id)
                    .ifPresentOrElse(clienteSalvo -> {
                                cliente.setId(id);
                                String cpf = clienteSalvo.getCpf();

                                if(clienteRepo.existsByCpf(cliente.getCpf()) && !cpf.equals(cliente.getCpf())) {
                                    throw new CPFAlreadyExistsException();
                                } else {
                                    clienteRepo.save(cliente);
                                }
                            }, () -> new ClienteNotFoundException("Cliente de id " + id + ", não encontrado!"));
    }

    @Override
    public List<Cliente> findClientesByFilter(Cliente filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return clienteRepo.findAll(example);
    }

}
