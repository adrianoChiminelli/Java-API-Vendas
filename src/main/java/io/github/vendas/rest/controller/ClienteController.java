package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Cliente;
import io.github.vendas.service.impl.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteServiceImpl;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteServiceImpl.findAllClientes();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Long id) {
        return clienteServiceImpl.findClienteById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')")
    @GetMapping("/buscar")
    public List<Cliente> getClientesByFilter(Cliente filter) {
        return  clienteServiceImpl.findClientesByFilter(filter);
    }

    @PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveNewCliente(@RequestBody @Valid Cliente cliente) {
        return clienteServiceImpl.SaveCliente(cliente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable Long id, @RequestBody @Valid Cliente cliente) {
        clienteServiceImpl.updateCliente(id, cliente);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable Long id) {
        clienteServiceImpl.deleteCliente(id);
    }

}
