package com.demo.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Account",
    description = "Schema to hold Account and Customer information"
)
public class AccountDto {
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    @Schema(
        description = "Account number",
        example = "1234567890"
    )
    private String accountNumber;

    @NotEmpty(message = "Account type cannot be null or empty")
    @Schema(
        description = "Account type",
        example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null or empty")
    @Schema(
        description = "Address of the branch",
        example = "123 Dummy Street, Dummy Town"
    )
    private String branchAddress;
    
    CustomerDto customerDto;
}
