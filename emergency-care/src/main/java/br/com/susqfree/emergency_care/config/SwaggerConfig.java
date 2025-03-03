package br.com.susqfree.emergency_care.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SUSQFree - Emergency Care API",
                version = "1.0.0",
                description = "Microservice for managing emergency care queues and patient attendance in the SUS system"
        )
)
public class SwaggerConfig {
}
