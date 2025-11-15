package com.example.product_management_API.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Management API")
                        .description("API para gerenciamento de produtos e autenticação")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Product Management API")
                        )
                );
    }
}

