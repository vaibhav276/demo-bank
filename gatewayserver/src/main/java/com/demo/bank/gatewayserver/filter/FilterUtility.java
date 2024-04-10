package com.demo.bank.gatewayserver.filter;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID_HDR = "X-DemoBank-Correlation-ID";

    public String getCorrelationId(HttpHeaders httpHeaders) {
        List<String> headers = httpHeaders.get(CORRELATION_ID_HDR);
        if (headers == null) return null;
        return headers.stream().findFirst().get();
    }

    public String generateNewCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID_HDR, correlationId);
    }
}
