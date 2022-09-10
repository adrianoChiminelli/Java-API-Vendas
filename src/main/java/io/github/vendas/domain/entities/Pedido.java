package io.github.vendas.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(precision = 20, scale = 2)
    private BigDecimal valorTotal;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPedido;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido")
    private Set<ItemPedido> itensPedido;

    public Pedido(Cliente cliente, BigDecimal valorTotal, LocalDate dataPedido, Set<ItemPedido> itensPedido) {
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.dataPedido = dataPedido;
        this.itensPedido = itensPedido;
    }
}
