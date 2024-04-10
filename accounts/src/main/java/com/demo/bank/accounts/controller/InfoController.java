package com.demo.bank.accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bank.accounts.dto.ContactInfoDto;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Info APIs"
)
public class InfoController {

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private ContactInfoDto contactInfoDto;

    public static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Operation(
        summary = "Get Build info"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "200 OK",
            content = @Content(
                schema = @Schema(implementation = String.class)
            )
        )
    }
    )
    @GetMapping("/info/build")
    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    public ResponseEntity<String> getBuildInfo() {

        logger.debug("getBuildInfo() invoked");

        return ResponseEntity
            .ok()
            .body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {

        logger.debug("getBuildInfoFallback() invoked");

        return ResponseEntity
            .ok()
            .body("0.0"); // fallback version
    }

    @Operation(
        summary = "Get Contact info"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "200 OK",
            content = @Content(
                schema = @Schema(implementation = ContactInfoDto.class)
            )
        )
    }
    )
    @GetMapping("/info/contact")
    public ResponseEntity<ContactInfoDto> getContactInfo() {
        return ResponseEntity
            .ok()
            .body(contactInfoDto);
    }
    
}
