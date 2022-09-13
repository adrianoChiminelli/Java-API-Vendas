package io.github.vendas.service.impl;

import io.github.vendas.domain.entities.Usuario;
import io.github.vendas.domain.repository.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findUsuarioByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
    }
}
