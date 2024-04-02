package com.demo.bank.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bank.accounts.constants.SuccessMessages;
import com.demo.bank.accounts.dto.AccountDto;
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

    @GetMapping("/accounts")
    public ResponseEntity<AccountDto> getAccountByCustomerMobileNumber(
        @RequestParam String mobileNumber
    ) {
        AccountDto accountDto = iAccountService.getByCustomerMobileNumber(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountDto);
    }

    @PutMapping("/accounts/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(
        @PathVariable(name = "accountNumber") Long accountNumber,
        @RequestBody AccountDto accountDto
    ) {
        AccountDto accountDtoNew = iAccountService.updateAccount(accountNumber, accountDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountDtoNew);
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<ResponseDto> deleteAccountByCustomerMobileNumber(
        @RequestParam String mobileNumber
    ) {
        boolean isDeleted = iAccountService.deleteByCustomerMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), SuccessMessages.ACCOUNT_DELETED));
        } else {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.toString()));
        }
    }
}
