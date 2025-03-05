package br.com.susqfree.doctor_management.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Doctor Management API",
                version = "1.0.0",
                description = "API para gerenciamento de médicos e especialidades"
        )
)
public class SwaggerConfig {
}
