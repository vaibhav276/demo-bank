package com.demo.bank.cards.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "contact")
public record ContactInfoDto(
    String message,
    Map<String, String> contactDetails, 
    List<String> onCallSupport) {
}
