package br.com.susqfree.health_unit_management.infra.config.swagger;

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
                .components(new Components())
                .info(new Info()
                        .title("Health Unit Management")
                        .description("SUSQFree - Health Unit Management")
                        .version("v1")
                        .contact(new Contact()
                                .name("SUSQFree")));

    }
}
