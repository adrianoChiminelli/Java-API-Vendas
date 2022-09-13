package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByNomeUsuario(String nomeUsuario);

}
