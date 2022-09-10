package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.exceptions.ClienteNotFoundExceprion;
import io.github.vendas.exceptions.InvalidCPFException;
import io.github.vendas.rest.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAllClientes();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteService.findClienteById(id);
    }

    @GetMapping("/buscar")
    public List<Cliente> getClientesByFilter(Cliente filter) {
        return  clienteService.findClientesByFilter(filter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveNewCliente(@RequestBody Cliente cliente) {
        try {
            return clienteService.SaveCliente(cliente);
        } catch (InvalidCPFException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            clienteService.updateCliente(id, cliente);
        } catch (InvalidCPFException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }

}
