package io.github.vendas.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Campo nome não pode estar vazio!")
    private String nome;

    @CPF(message = "CPF informado é inválido!")
    @NotEmpty(message = "Campo CPF não pode estar vazio!")
    @Column(unique = true, length = 11, nullable = false)
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> listaPedidos;

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Set<Pedido> listaPedidos) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.listaPedidos = listaPedidos;
    }
}
