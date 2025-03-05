package br.com.fiap.triage_service.infra.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("PageableObject", null))
                .info(new Info()
                        .title("Triage Service")
                        .description("SUSQFree - Triage Service")
                        .version("v1")
                        .contact(new Contact()
                                .name("SUSQFree")));

    }
}
