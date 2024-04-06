package com.demo.bank.loans.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "loans")
public record ContactInfoDto(
    String message,
    Map<String, String> contactDetails, 
    List<String> onCallSupport) {
}
