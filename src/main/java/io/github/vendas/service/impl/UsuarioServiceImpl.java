package io.github.vendas.service.impl;

import io.github.vendas.domain.entities.Usuario;
import io.github.vendas.domain.repository.UsuarioRepo;
import io.github.vendas.exceptions.UsuarioNotFoundException;
import io.github.vendas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder encoder;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        String encodedPassword = encoder.encode(usuario.getSenha());
        usuario.setSenha(encodedPassword);
        return usuarioRepo.save(usuario);
    }

    @Override
    public List<Usuario> findAllUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com id: " + id + ", não encontrado!"));
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepo.findById(id)
                .ifPresentOrElse(usuario -> usuarioRepo.deleteById(id),
                        () -> new UsuarioNotFoundException("Usuário com id: " + id + ", não encontrado!"));
    }

    @Override
    public void updateUsuario(Long id, Usuario usuario) {
        usuarioRepo.findById(id)
                .ifPresentOrElse(usuarioSalvo -> {
                    usuario.setId(id);
                    usuarioRepo.save(usuario);
                }, () -> new UsuarioNotFoundException("Usuário com id: " + id + ", não encontrado!"));
    }

    @Override
    public List<Usuario> findUsuarioByFilter(Usuario filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return usuarioRepo.findAll(example);
    }
}
