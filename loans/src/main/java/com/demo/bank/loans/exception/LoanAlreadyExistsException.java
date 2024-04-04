package com.demo.bank.loans.exception;

import com.demo.bank.loans.constants.ExceptionMessages;

public class LoanAlreadyExistsException extends RuntimeException {
    public LoanAlreadyExistsException(String mobileNumber) {
        super(String.format(ExceptionMessages.LOAN_EXISTS_MOBILE_NUMBER, mobileNumber));
    }
}
