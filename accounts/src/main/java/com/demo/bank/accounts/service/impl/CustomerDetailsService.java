package com.demo.bank.accounts.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.bank.accounts.dto.AccountDto;
import com.demo.bank.accounts.dto.CardDto;
import com.demo.bank.accounts.dto.CustomerDetailsDto;
import com.demo.bank.accounts.dto.LoanDto;
import com.demo.bank.accounts.mapper.AccountMapper;
import com.demo.bank.accounts.mapper.CustomerDetailsMapper;
import com.demo.bank.accounts.service.IAccountService;
import com.demo.bank.accounts.service.ICustomerDetailsService;
import com.demo.bank.accounts.service.client.CardsFeignClient;
import com.demo.bank.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerDetailsService implements ICustomerDetailsService {

    CardsFeignClient cardsFeignClient;
    LoansFeignClient loansFeignClient;
    IAccountService iAccountService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsService.class);

    @Override
    public CustomerDetailsDto getByMobileNumber(String correlationId, String mobileNumber) {

        logger.debug("[{}]: Getting details from downstream services by mobile number {}", correlationId, mobileNumber);

        AccountDto accountDto = iAccountService.getByCustomerMobileNumber(mobileNumber);
        ResponseEntity<CardDto> responseEntityCardDto = cardsFeignClient.getByMobileNumber(correlationId, mobileNumber);
        ResponseEntity<LoanDto> responseEntityLoanDto = loansFeignClient.getByMobileNumber(correlationId, mobileNumber);

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setAccountDto(accountDto);
        CustomerDetailsMapper.mapToCustomerDetailsDto(accountDto.getCustomerDto(), customerDetailsDto);
        customerDetailsDto.setCardDto(responseEntityCardDto.getBody());
        customerDetailsDto.setLoanDto(responseEntityLoanDto.getBody());

        return customerDetailsDto;
    }
    
}
