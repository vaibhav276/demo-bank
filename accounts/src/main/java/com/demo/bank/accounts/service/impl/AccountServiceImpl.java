package com.demo.bank.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.demo.bank.accounts.constants.AccountsConstants;
import com.demo.bank.accounts.constants.ExceptionMessages;
import com.demo.bank.accounts.dto.AccountDto;
import com.demo.bank.accounts.dto.CustomerDto;
import com.demo.bank.accounts.entity.Account;
import com.demo.bank.accounts.entity.Customer;
import com.demo.bank.accounts.exception.AccountNumberMismatchException;
import com.demo.bank.accounts.exception.CustomerAlreadyExistsException;
import com.demo.bank.accounts.exception.ResourceNotFoundException;
import com.demo.bank.accounts.mapper.AccountMapper;
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
                ExceptionMessages.CUSTOMER_MOBILE_NUMBER_EXISTS 
                + customerDto.getMobileNumber());
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

    @Override
    public AccountDto getByCustomerMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException(
                "Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "Account", "customerID" , 
                customer.getCustomerId().toString())
        );

        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());
        accountDto.setCustomerDto(CustomerMapper.mapToCustomerDto(customer, new CustomerDto()));

        return accountDto;
    }

    @Override
    public AccountDto updateAccount(Long accountNumber, AccountDto accountDto) {
        Account account = accountRepository.findById(accountNumber).orElseThrow(
            () -> new ResourceNotFoundException(
                "Account", "accountNumber", 
                Long.toString(accountNumber))
        );
        if (accountDto.getAccountNumber() != null &&
            accountDto.getAccountNumber().compareTo(accountNumber) != 0) {
            throw new AccountNumberMismatchException(
                String.format(ExceptionMessages.ACCOUNT_NUMBER_MISMATCH, 
                accountNumber, accountDto.getAccountNumber()));
        }

        Account accountNew = AccountMapper.mapToAccount(accountDto, new Account());
        if (accountNew.getAccountType() != null) {
            account.setAccountType(accountNew.getAccountType());
        }
        if (accountNew.getBranchAddress() != null) {
            account.setBranchAddress(accountNew.getBranchAddress());
        }

        if (accountDto.getCustomerDto() != null) {
            Customer customerNew = CustomerMapper.mapToCustomer(accountDto.getCustomerDto(), new Customer());
            Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException(
                    "Customer", "customerId", 
                    Long.toString(account.getCustomerId()))
            );
            if (customerNew.getEmail() != null) {
                customer.setEmail(customerNew.getEmail());
            }
            if (customerNew.getMobileNumber() != null) {
                customer.setEmail(customerNew.getEmail());
            }
            if (customerNew.getName() != null) {
                customer.setName(customerNew.getName());
            }
            customerRepository.save(customer);
        }
        accountRepository.save(account);

        AccountDto accountDtoNew = AccountMapper.mapToAccountDto(account, new AccountDto());
        Customer customerNew = customerRepository.findById(account.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException(
                "Customer", "customerId", 
                Long.toString(account.getCustomerId()))
        );
        accountDtoNew.setCustomerDto(CustomerMapper.mapToCustomerDto(customerNew, new CustomerDto()));
        return accountDtoNew;
    }

    @Override
    public boolean deleteByCustomerMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException(
                "Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

}
