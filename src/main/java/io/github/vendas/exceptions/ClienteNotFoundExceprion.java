package io.github.vendas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClienteNotFoundExceprion extends ResponseStatusException {
    public ClienteNotFoundExceprion(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
