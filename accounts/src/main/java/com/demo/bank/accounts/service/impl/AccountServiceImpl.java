package com.demo.bank.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.demo.bank.accounts.constants.AccountsConstants;
import com.demo.bank.accounts.constants.ExceptionMessages;
import com.demo.bank.accounts.dto.CustomerDto;
import com.demo.bank.accounts.entity.Account;
import com.demo.bank.accounts.entity.Customer;
import com.demo.bank.accounts.exception.CustomerAlreadyExistsException;
import com.demo.bank.accounts.mapper.CustomerMapper;
import com.demo.bank.accounts.repository.AccountRepository;
import com.demo.bank.accounts.repository.CustomerRepository;
import com.demo.bank.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    AccountRepository accountRepository;
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(
            customerDto.getMobileNumber()
        );
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(
                ExceptionMessages.CUSTOMER_MOBILE_NUMBER_EXISTS);
        }

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.ACCOUNT_TYPE_SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ACCOUNT_DUMMY_BRANCH_ADDR);

        return newAccount;
    }

}
