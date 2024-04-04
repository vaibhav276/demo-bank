package com.demo.bank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
    name = "Response"
)
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}