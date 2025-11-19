package com.sopt.collaboration.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${spring.swagger.base-url:http://localhost:8080}")
	private String baseUrl;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("SOPT Collaboration API")
				.description("SOPT Collaboration Seminar API Documentation")
				.version("v1.0.0"))
			.servers(List.of(
				new Server()
					.url(baseUrl)
					.description("Local Server")
			));
	}
}
