package com.demo.bank.accounts.service;

import com.demo.bank.accounts.dto.CustomerDto;

public interface IAccountService {
    /**
     * Creates a new account for customer
     * @param customerDto - CustomerDto object
     */
    public void createAccount(CustomerDto customerDto);
}
