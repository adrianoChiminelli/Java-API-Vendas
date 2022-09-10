package io.github.vendas.rest.service;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.domain.repository.ClienteRepo;
import io.github.vendas.exceptions.ClienteNotFoundExceprion;
import io.github.vendas.exceptions.InvalidCPFException;
import io.github.vendas.utils.CPFManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepo clienteRepo;
    private final CPFManager cpfManager;

    public ClienteService(ClienteRepo clienteRepo, CPFManager cpfManager) {
        this.clienteRepo = clienteRepo;
        this.cpfManager = cpfManager;
    }

    public Cliente SaveCliente(Cliente cliente) throws InvalidCPFException {
        if ( clienteRepo.existsByCpf(cliente.getCpf()) ) {
            if ( cpfManager.checkCPF(cliente.getCpf()) ) {
                return clienteRepo.save(cliente);
            } else {
                throw new InvalidCPFException("CPF Informado é inválido.");
            }
        } else {
            throw new InvalidCPFException("CPF Informado já está cadastrado.");
        }
    }

    public List<Cliente> findAllClientes(){
        return clienteRepo.findAll();
    }

    public Cliente findClienteById(Long id){
        return clienteRepo.findById(id)
                .orElseThrow(() -> new ClienteNotFoundExceprion("Cliente de id " + id + ", não encontrado!"));
    }

    public void deleteCliente(Long id) {
        clienteRepo.findById(id)
                .ifPresentOrElse(cliente -> clienteRepo.deleteById(id),
                        () -> new ClienteNotFoundExceprion("Cliente de id " + id + ", não encontrado!"));
    }

    public void updateCliente(Cliente cliente) throws InvalidCPFException {
        if (cpfManager.checkCPF(cliente.getCpf())){

            clienteRepo.findById(cliente.getId())
                    .ifPresentOrElse(clienteSalvo -> clienteRepo.save(cliente),
                            () -> new ClienteNotFoundExceprion("Cliente de id " + cliente.getId() + ", não encontrado!"));

        } else {
            throw new InvalidCPFException("CPF Informado é inválido.");
        }

    }

    public Cliente findClienteByCPF(String cpf) {
        return clienteRepo.findByCpfLike(cpf)
                .orElseThrow(() -> new ClienteNotFoundExceprion("Cliente de cpf: " + cpf + ", não encontrado!"));
    }

    public List<Cliente> findClientesByFilter(Cliente filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return clienteRepo.findAll(example);
    }

}
