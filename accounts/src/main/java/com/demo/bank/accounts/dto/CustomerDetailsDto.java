package com.demo.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer Details",
    description = "Schema to hold Customer details including accounts, loans and cards information linked using mobile number"
)
public class CustomerDetailsDto {
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    @Schema(
        description = "Customer name",
        example = "Bruce Wayne"
    )
    private String name;

    @Email(message = "Email address not in valid format")
    @Schema(
        description = "Customer email",
        example = "bruce@wayne.com"
    )
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @Schema(
        description = "Customer mobile number. Used as a key in many operations",
        example = "9087654321"
    )
    private String mobileNumber;

    AccountDto accountDto;
    CardDto cardDto;
    LoanDto loanDto;
}
