package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.exceptions.InvalidCPFException;
import io.github.vendas.service.impl.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteServiceImpl;

    public ClienteController(ClienteServiceImpl clienteServiceImpl) {
        this.clienteServiceImpl = clienteServiceImpl;
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteServiceImpl.findAllClientes();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteServiceImpl.findClienteById(id);
    }

    @GetMapping("/buscar")
    public List<Cliente> getClientesByFilter(Cliente filter) {
        return  clienteServiceImpl.findClientesByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveNewCliente(@RequestBody Cliente cliente) {
        try {
            return clienteServiceImpl.SaveCliente(cliente);
        } catch (InvalidCPFException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            clienteServiceImpl.updateCliente(id, cliente);
        } catch (InvalidCPFException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) {
        clienteServiceImpl.deleteCliente(id);
    }

}
