package com.demo.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
    name = "Loan"
)
public class LoanDto {
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    @Schema(
        description = "Mobile number. Used as key in many operations",
        example = "9087654321"
    )
    private String mobileNumber;

    @Pattern(regexp = "^$|[0-9]{12}", message = "Loan number must be 12 digits")
    @Schema(
        description = "Loan number",
        example = "123456789012"
    )
    private String loanNumber;

    @NotEmpty(message = "Loan type cannot be null or empty")
    @Schema(
        description = "Loan type",
        example = "Home Loan"
    )
    private String loanType;

    @PositiveOrZero(message = "Total loan must be 0 or higher")
    @Schema(
        description = "Total Loan amount",
        example = "1232"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Amount paid must be 0 or higher")
    @Schema(
        description = "Amount paid",
        example = "233"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Outstanding amount must be 0 or higher")
    @Schema(
        description = "Outstanding amount",
        example = "348"
    )
    private int outstandingAmount;
}

