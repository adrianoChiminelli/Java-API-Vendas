package io.github.vendas.rest.dto;

import io.github.vendas.validation.NotEmptySet;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoDTO {

    @NotNull(message = "Id do cliente deve ser informado!")
    private Long idCliente;

    @NotEmptySet(message = "Lista de itens não pode estar vazia.")
    private Set<ItemPedidoDTO> itens;
}

