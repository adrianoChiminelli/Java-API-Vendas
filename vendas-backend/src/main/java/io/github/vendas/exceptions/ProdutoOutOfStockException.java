package io.github.vendas.exceptions;

public class ProdutoOutOfStockException extends RuntimeException {
    public ProdutoOutOfStockException(String message) {
        super(message);
    }
}
