package com.demo.bank.loans.exception;

import com.demo.bank.loans.constants.ExceptionMessages;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String value) {
        super(String.format(ExceptionMessages.RESOURCE_NOT_FOUND, resourceName, fieldName, value));
    }
}
