package com.demo.bank.accounts.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "accounts")
public record ContactInfoDto(
    String message,
    Map<String, String> contactDetails, 
    List<String> onCallSupport) {
}
