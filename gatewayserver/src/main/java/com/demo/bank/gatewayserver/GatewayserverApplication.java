package com.demo.bank.gatewayserver;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator demobankRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
			.route(p -> p
				.path("/demobank/accounts/**")
				.filters(f -> f
					.rewritePath("/demobank/accounts/(?<segment>)", "/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
					.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
													.setFallbackUri("forward:/contact-support")
					)
				)
				.uri("lb://ACCOUNTS")
			)
			.route(p -> p
				.path("/demobank/loans/**")
				.filters(f -> f
					.rewritePath("/demobank/loans/(?<segment>)", "/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
					.retry(config -> 
						config
						.setRetries(3)
						.setMethods(HttpMethod.GET)
						.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
					)
				)
				.uri("lb://LOANS")
			)
			.route(p -> p
				.path("/demobank/cards/**")
				.filters(f -> f
					.rewritePath("/demobank/cards/(?<segment>)", "/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
				)
				.uri("lb://CARDS")
			)
			.build();
	}
}
