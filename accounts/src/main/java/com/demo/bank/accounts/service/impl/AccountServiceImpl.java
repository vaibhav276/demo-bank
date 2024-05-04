package com.demo.bank.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.demo.bank.accounts.constants.AccountsConstants;
import com.demo.bank.accounts.constants.ExceptionMessages;
import com.demo.bank.accounts.dto.AccountDto;
import com.demo.bank.accounts.dto.AccountsMsgDto;
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
    private final StreamBridge streamBridge;

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

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
        Account savedAccount = accountRepository.save(createNewAccount(savedCustomer));

        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Account account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
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
            accountDto.getAccountNumber().compareTo(Long.toString(accountNumber)) != 0) {
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

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Account account = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return  isUpdated;
    }
}
