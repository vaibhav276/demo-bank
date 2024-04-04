package com.demo.bank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
    name = "Card",
    description = "Card details"
)
public class CardDto {
    @Schema(
        description = "Customer mobile number. Used as key in may operations",
        example = "9087654321"
    )
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
        description = "Card Number",
        example = "1234567890"
    )
    @Pattern(regexp = "^$|[0-9]{10}", message = "Card number must be 10 digits")
    private String cardNumber;

    @Schema(
        description = "Card Type",
        example = "Credit Card"
    )
    @NotEmpty(message = "Card type must not be null or empty")
    private String cardType;

    @Schema(
        description = "Total limit",
        example = "9834"
    )
    @PositiveOrZero(message = "Total limit must be 0 or higher")
    private int totalLimit;

    @Schema(
        description = "Amount used",
        example = "12"
    )
    @PositiveOrZero(message = "Amount used must be 0 or higher")
    private int amountUsed;

    @Schema(
        description = "Amount available",
        example = "334"
    )
    @PositiveOrZero(message = "Available amount must be 0 or higher")
    private int availableAmount;
}
