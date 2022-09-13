package io.github.vendas.service;

import io.github.vendas.domain.entities.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario saveUsuario(Usuario usuario);

    List<Usuario> findAllUsuarios();

    Usuario findUsuarioById(Long id);

    void deleteUsuario(Long id);

    void updateUsuario(Long id, Usuario usuario);

    List<Usuario> findUsuarioByFilter(Usuario filter);
}
