package com.demo.bank.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.bank.cards.constants.ExceptionMessages;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(
        String resourceName,
        String queryByField,
        String value
    ) {
        super(String.format(ExceptionMessages.RESOURCE_NOT_FOUND,
            resourceName, queryByField, value));
    }
}
