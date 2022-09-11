package io.github.vendas.rest.dto;

import io.github.vendas.validation.NotEmptyList;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoDTO {

    @NotNull(message = "Id do cliente deve ser informado!")
    private Long idCliente;
    @NotEmptyList()
    private Set<ItemPedidoDTO> itens;
}

