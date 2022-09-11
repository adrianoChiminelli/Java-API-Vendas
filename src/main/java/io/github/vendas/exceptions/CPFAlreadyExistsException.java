package io.github.vendas.exceptions;

public class CPFAlreadyExistsException extends RuntimeException {
    public CPFAlreadyExistsException() {
        super("Já existe um cliente cadastrado com esse CPF!");
    }
}
