package jp.co.access.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author HaiTV
 */

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Value("${server.port}")
    private String SERVER_PORT;

    @Value("${application.version}")
    private String APPLICATION_VERSION;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .servers(Arrays.asList(
                        new Server().url(String.format("http://localhost:%s/", SERVER_PORT))
                ))
                .info(new Info()
                        .title("Access Auth Application API")
                        .version(APPLICATION_VERSION)
                ).addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
