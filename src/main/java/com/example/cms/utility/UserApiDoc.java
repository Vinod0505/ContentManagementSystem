package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class UserApiDoc {

	@Bean
	Contact contact() {
		return new Contact()
				.email("test@gmail")
				.name("TestName")
				.url("test.com");
	}
	
	@Bean
	Info info() {
		return new Info()
				.title("ContentManagementSystem")
				.description("Restful api with basic crud opreation")
				.version("v1")
				.contact(contact());
	}
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}

}
