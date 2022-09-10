package io.github.vendas.service.impl;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.domain.repository.ClienteRepo;
import io.github.vendas.exceptions.ClienteNotFoundException;
import io.github.vendas.exceptions.InvalidCPFException;
import io.github.vendas.service.ClienteService;
import io.github.vendas.utils.CPFManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepo clienteRepo;
    private final CPFManager cpfManager;

    @Override
    public Cliente SaveCliente(Cliente cliente) throws InvalidCPFException {
        if (!clienteRepo.existsByCpf(cliente.getCpf())) {
            if ( cpfManager.checkCPF(cliente.getCpf()) ) {
                return clienteRepo.save(cliente);
            } else {
                throw new InvalidCPFException("CPF Informado é inválido.");
            }
        } else {
            throw new InvalidCPFException("CPF Informado já está cadastrado.");
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
    public void updateCliente(Long id, Cliente cliente) throws InvalidCPFException {
        if (cpfManager.checkCPF(cliente.getCpf())){

            clienteRepo.findById(id)
                    .ifPresentOrElse(c -> {
                                cliente.setId(id);
                                clienteRepo.save(cliente);
                            }, () -> new ClienteNotFoundException("Cliente de id " + id + ", não encontrado!"));

        } else {
            throw new InvalidCPFException("CPF Informado é inválido.");
        }

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
