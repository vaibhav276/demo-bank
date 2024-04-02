package com.demo.bank.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bank.accounts.constants.SuccessMessages;
import com.demo.bank.accounts.dto.CustomerDto;
import com.demo.bank.accounts.dto.ResponseDto;
import com.demo.bank.accounts.service.IAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountsController {

    IAccountService iAccountService;

    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(
        @RequestBody CustomerDto customerDto
    ) {

        iAccountService.createAccount(customerDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(HttpStatus.CREATED.toString(),
                                  SuccessMessages.ACCOUNT_CREATED));
    }
}
