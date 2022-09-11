package io.github.vendas.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoDTO {

    private Long idCliente;
    private Set<ItemPedidoDTO> itens;
}

