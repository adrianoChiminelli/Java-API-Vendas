package io.github.vendas.rest.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long idProduto;
    private Integer quantidade;
}
