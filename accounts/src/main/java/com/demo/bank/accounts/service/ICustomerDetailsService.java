package com.demo.bank.accounts.service;

import com.demo.bank.accounts.dto.CustomerDetailsDto;

public interface ICustomerDetailsService {

    /**
     * @param mobileNumber
     * @return CustomerDetailsDto
     */
    CustomerDetailsDto getByMobileNumber(String correlationId, String mobileNumber);
}
