package com.demo.bank.accounts.mapper;

import com.demo.bank.accounts.dto.CustomerDetailsDto;
import com.demo.bank.accounts.dto.CustomerDto;

public class CustomerDetailsMapper {
    public static CustomerDetailsDto mapToCustomerDetailsDto(
        CustomerDto customerDto, 
        CustomerDetailsDto customerDetailsDto) {

        customerDetailsDto.setName(customerDto.getName());
        customerDetailsDto.setEmail(customerDto.getEmail());
        customerDetailsDto.setMobileNumber(customerDto.getMobileNumber());
        return customerDetailsDto;
    }
}
