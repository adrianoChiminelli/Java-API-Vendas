package io.github.vendas.rest.controller;

import io.github.vendas.exceptions.*;
import io.github.vendas.rest.ApiErrors;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(CPFAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleInvalidCPFException(CPFAlreadyExistsException ex) {
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleClienteNotFoundException(ClienteNotFoundException ex) {
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNotFoundException ex) {
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleProdutoNotFoundException(ProdutoNotFoundException ex) {
        String mensagem = ex.getMessage();
        return new ApiErrors(mensagem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

    @ExceptionHandler(ProdutoOutOfStockException.class)
    @ResponseStatus(INSUFFICIENT_STORAGE)
    public ApiErrors handleProdutoOutOfStockException(ProdutoOutOfStockException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        String message = ex.getMessage();
        return new ApiErrors(message);
    }
}
