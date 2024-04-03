package com.demo.bank.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.demo.bank.accounts.dto.ErrorResponseDto;
import com.demo.bank.accounts.dto.ResponseDto;
import com.demo.bank.accounts.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts in DemoBank",
        description = "CRUD REST APIs in DemoBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
public class AccountsController {

    IAccountService iAccountService;

    @Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside DemoBank"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "201 CREATED"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "400 BAD REQUEST",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "500 INTERNAL SERVER ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @PostMapping("/accounts")
    public ResponseEntity<ResponseDto> createAccount(
        @Valid @RequestBody CustomerDto customerDto
    ) {

        iAccountService.createAccount(customerDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(HttpStatus.CREATED.toString(),
                                  SuccessMessages.ACCOUNT_CREATED));
    }

    @Operation(
        summary = "Fetch Account Details REST API",
        description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "200 OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "500 INTERNAL SERVER ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @GetMapping("/accounts")
    public ResponseEntity<AccountDto> getAccountByCustomerMobileNumber(
        @RequestParam
        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        AccountDto accountDto = iAccountService.getByCustomerMobileNumber(mobileNumber);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountDto);
    }

    @Operation(
        summary = "Update Account Details REST API",
        description = "REST API to update Customer & Account details based on a account number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "200 OK"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "404 NOT FOUND",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "400 BAD REQUEST",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "500 INTERNAL SERVER ERROR",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    }
    )
    @PutMapping("/accounts/{accountNumber}")
    public ResponseEntity<AccountDto> updateAccount(
        @PathVariable(name = "accountNumber") Long accountNumber,
        @Valid @RequestBody AccountDto accountDto
    ) {
        AccountDto accountDtoNew = iAccountService.updateAccount(accountNumber, accountDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountDtoNew);
    }

    @Operation(
        summary = "Delete Account & Customer Details REST API",
        description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "200 OK"
        ),
        @ApiResponse(
            responseCode = "417",
            description = "417 EXPECTATION FAILED"
        )
    }
    )
    @DeleteMapping("/accounts")
    public ResponseEntity<ResponseDto> deleteAccountByCustomerMobileNumber(
        @RequestParam
        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        boolean isDeleted = iAccountService.deleteByCustomerMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), SuccessMessages.ACCOUNT_DELETED));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(HttpStatus.EXPECTATION_FAILED.toString(),
                    HttpStatus.EXPECTATION_FAILED.toString()));
        }
    }
}
