package com.demo.bank.accounts.service;

import com.demo.bank.accounts.dto.AccountDto;
import com.demo.bank.accounts.dto.CustomerDto;

public interface IAccountService {
    /**
     * Creates a new account for customer
     * @param customerDto - CustomerDto object
     */
    public void createAccount(CustomerDto customerDto);

    /**
     * Gets customer details by mobile number
     * @param mobileNumber
     * @return AccountDto
     */
    public AccountDto getByCustomerMobileNumber(String mobileNumber);
}
