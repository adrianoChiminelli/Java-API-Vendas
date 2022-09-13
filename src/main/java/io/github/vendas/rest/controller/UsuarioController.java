package io.github.vendas.rest.controller;

import io.github.vendas.domain.entities.Usuario;
import io.github.vendas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveUsuario(@RequestBody @Valid Usuario usuario){
        usuarioService.saveUsuario(usuario);
        return String.format("Usuário %s cadastrado com sucesso!", usuario.getNomeUsuario());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Usuario> findAllUsuario(){
        return usuarioService.findAllUsuarios();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Usuario findUsuarioById(@PathVariable Long id) {
        return usuarioService.findUsuarioById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsuario(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        usuarioService.updateUsuario(id, usuario);
    }
}
