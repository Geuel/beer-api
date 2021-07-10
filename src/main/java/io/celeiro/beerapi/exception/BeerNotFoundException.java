package io.celeiro.beerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception {
    public BeerNotFoundException(Long id) {
        super("Beer not founded with ID " + id);
    }
}
