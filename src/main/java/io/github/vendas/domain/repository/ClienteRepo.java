package io.github.vendas.domain.repository;

import io.github.vendas.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpfLike(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsById(Long id);
}
