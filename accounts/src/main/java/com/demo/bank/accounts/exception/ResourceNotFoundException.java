package com.demo.bank.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(
        String resourceName,
        String queryByField,
        String value
    ) {
        super(String.format("Resource %s not found with field %s = %s", 
            resourceName, queryByField, value));
    }
}
