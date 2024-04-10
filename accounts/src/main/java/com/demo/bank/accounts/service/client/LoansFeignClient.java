package com.demo.bank.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.bank.accounts.constants.AccountsConstants;
import com.demo.bank.accounts.dto.LoanDto;

@FeignClient("loans")
public interface LoansFeignClient {
    
    @GetMapping(value = "/api/v1/loans", consumes = "application/json")
    public ResponseEntity<LoanDto> getByMobileNumber(
        @RequestHeader(name = AccountsConstants.CORRELATION_ID_HDR) String correlationId,
        @RequestParam String mobileNumber);
}
