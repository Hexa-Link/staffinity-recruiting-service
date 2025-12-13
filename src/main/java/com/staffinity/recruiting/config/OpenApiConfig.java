package com.staffinity.recruiting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Staffinity Recruiting API", version = "1.0.0", description = "API for staffinity recruiting service."),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Enter JWT Bearer token"
)
public class OpenApiConfig {

    private static io.swagger.v3.oas.models.info.Info info;

    @Bean
    public OpenAPI staffinityOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080/api");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setName("Hexa-Link Team");
        contact.setUrl("https://github.com/Hexa-Link");
        contact.setEmail("hexa-link@gmail.com");

        License license = new License();
        license.setName("GNU GENERAL PUBLIC LICENSE");
        license.setUrl("https://opensource.org/licenses/GPL-3.0");

        info = new io.swagger.v3.oas.models.info.Info();
        info.setTitle("Staffinity Recruiting API");
        info.setVersion("1.0.0");
        info.setDescription("API for managing recruiting, candidates, vacancies, and internal talent search.");
        info.setContact(contact);
        info.setLicense(license);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
}
