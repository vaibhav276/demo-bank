package com.demo.bank.accounts.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bank.accounts.dto.CustomerDetailsDto;
import com.demo.bank.accounts.dto.ErrorResponseDto;
import com.demo.bank.accounts.service.ICustomerDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
        name = "REST APIs for Customer Details in DemoBank",
        description = "REST APIs in DemoBank to get all customer details (including from other services)"
)
public class CustomerDetailsController {
    ICustomerDetailsService iCustomerDetailsService;

    @Operation(
        summary = "Get Customer Details REST API",
        description = "REST API to get all Customer details based on a mobile number"
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
    @GetMapping("/customers")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetailsByMobileNumber(
        @RequestParam
        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        CustomerDetailsDto customerDetailsDto = iCustomerDetailsService.getByMobileNumber(mobileNumber);
        return ResponseEntity
            .ok()
            .body(customerDetailsDto);
    }
}
