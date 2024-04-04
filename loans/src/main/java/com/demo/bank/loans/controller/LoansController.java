package com.demo.bank.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bank.loans.constants.SuccessMessages;
import com.demo.bank.loans.dto.ErrorResponseDto;
import com.demo.bank.loans.dto.LoanDto;
import com.demo.bank.loans.dto.ResponseDto;
import com.demo.bank.loans.service.ILoanService;

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
@AllArgsConstructor
@RequestMapping("/api/v1")
@Validated
@Tag(
        name = "CRUD REST APIs for Loans in DemoBank",
        description = "CRUD REST APIs in DemoBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
public class LoansController {

    ILoanService iLoanService;

    @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create new Loan inside DemoBank"
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
    @PostMapping("/loans")
    public ResponseEntity<ResponseDto> createLoan(
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber) {
        iLoanService.createLoan(mobileNumber);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(
                HttpStatus.CREATED.toString(),
                SuccessMessages.LOAN_CREATED
            ));
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch Loan details based on a mobile number"
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
    @GetMapping("/loans")
    public ResponseEntity<LoanDto> getByMobileNumber(
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber) {
        LoanDto loanDto = iLoanService.getByMobileNumber(mobileNumber);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(loanDto);
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update Loan details based on card number"
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
            responseCode = "417",
            description = "417 EXPECTATION FAILED"
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
    @PutMapping("/loans")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = iLoanService.updateLoanDetails(loanDto);
        if (isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(
                    HttpStatus.OK.toString(),
                    SuccessMessages.LOAN_UPDATED
                ));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(
                    HttpStatus.EXPECTATION_FAILED.toString(),
                    HttpStatus.EXPECTATION_FAILED.toString()
                ));
        }
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
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
    @DeleteMapping("/loans")
    public ResponseEntity<ResponseDto> deleteLoan(
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber) {
        boolean isDeleted = iLoanService.deleteByMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(
                    HttpStatus.OK.toString(),
                    SuccessMessages.LOAN_UPDATED
                ));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(
                    HttpStatus.EXPECTATION_FAILED.toString(),
                    HttpStatus.EXPECTATION_FAILED.toString()
                ));
        }
    }
}
