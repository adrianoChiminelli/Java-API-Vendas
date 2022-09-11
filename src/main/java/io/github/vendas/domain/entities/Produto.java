package io.github.vendas.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Campo descrição do produto não pode estar vazio!")
    private String descricao;

    @Column(precision = 20, scale = 2)
    @NotNull(message = "O preço do produto deve ser informado!")
    private BigDecimal preco;

    @NotNull(message = "A quantidade de estoque do produto deve ser informado!")
    private Integer quantidadeEstoque;

    public Produto(String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
