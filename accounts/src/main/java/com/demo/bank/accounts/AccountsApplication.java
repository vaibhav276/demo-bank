package com.demo.bank.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	info = @Info(
		title = "Accounts service REST API Documentation",
		description = "DemoBank Accounts service REST API Documentation",
		version = "v1",
		contact = @Contact(
			name = "Vaibhav Pujari",
			email = "vaibhav@example.com",
			url = "https://www.example.com"
		),
		license = @License(
			name = "MIT",
			url = "https://www.example.com"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "DemoBank Accounts service REST API Documentation",
		url = "https://www.example.com/swagger-ui.html"
	)
)
public class AccountsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}