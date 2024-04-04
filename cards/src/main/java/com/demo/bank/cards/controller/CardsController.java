package com.demo.bank.cards.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.demo.bank.cards.constants.ExceptionMessages;
import com.demo.bank.cards.constants.SuccessMessages;
import com.demo.bank.cards.dto.CardDto;
import com.demo.bank.cards.dto.ErrorResponseDto;
import com.demo.bank.cards.dto.ResponseDto;
import com.demo.bank.cards.service.ICardService;

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
@Validated
@AllArgsConstructor
@Tag(
        name = "CRUD REST APIs for Cards in DemoBank",
        description = "CRUD REST APIs in DemoBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
public class CardsController {

    ICardService iCardService;

    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Card inside DemoBank"
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
    @PostMapping("/cards")
    public ResponseEntity<ResponseDto> createCard(
        @Valid
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam
        String mobileNumber) {
        iCardService.createCard(mobileNumber);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(HttpStatus.CREATED.toString(),
                SuccessMessages.CARD_CREATED));
    }

    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch Card details based on a mobile number"
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
    @GetMapping("/cards")
    public ResponseEntity<CardDto> getByMobileNumber(
        @Valid
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber) {
        CardDto cardDto = iCardService.getByMobileNumber(mobileNumber);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(cardDto);
    }

    @Operation(
        summary = "Update Card Details REST API",
        description = "REST API to update Card details based on card number"
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
    @PutMapping("/cards")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardDto cardDto) {
        boolean isUpdated = iCardService.updateCardDetails(cardDto);
        if (isUpdated) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), SuccessMessages.CARD_UPDATED));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(HttpStatus.EXPECTATION_FAILED.toString(),
                    ExceptionMessages.EXPECTATION_FAILED));
        }
    }

    @Operation(
        summary = "Delete Card Details REST API",
        description = "REST API to delete Card details based on a mobile number"
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
    @DeleteMapping("/cards")
    public ResponseEntity<ResponseDto> deleteByMobileNumber(
        @Valid
        @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digits")
        @RequestParam String mobileNumber) {
        boolean isDeleted = iCardService.deleteByMobileNumber(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK.toString(), SuccessMessages.CARD_DELETED));
        } else {
            return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(HttpStatus.EXPECTATION_FAILED.toString(),
                    ExceptionMessages.EXPECTATION_FAILED));
        }
    }
}
