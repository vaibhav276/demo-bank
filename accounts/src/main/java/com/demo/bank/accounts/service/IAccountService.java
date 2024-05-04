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

    /**
     * Updated any field of account except its account number
     * @param accountNumber - Account number of the account
     * @param accountDto - DTO containing new field values
     * @return
     */
    public AccountDto updateAccount(Long accountNumber, AccountDto accountDto);

    /**
     * Delete an account and associated customer by mobile number
     * @param mobileNumber
     * @return boolean - indicating if the deletion was successful
     */
    public boolean deleteByCustomerMobileNumber(String mobileNumber);

    /**
     * @param accountNumber
     */
    public boolean updateCommunicationStatus(Long accountNumber);
}
