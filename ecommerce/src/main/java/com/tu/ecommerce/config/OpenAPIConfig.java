package com.tu.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ECommerce API")
                        .version("1.0")
                        .description("ECommerce API Documentation"));
    }

    @Bean
    public GroupedOpenApi apiControllers() {
        return GroupedOpenApi.builder()
                .group("controllers")
                .packagesToScan("com.tu.ecommerce.controller")
                .pathsToMatch("/api/**")
                .build();
    }
}
