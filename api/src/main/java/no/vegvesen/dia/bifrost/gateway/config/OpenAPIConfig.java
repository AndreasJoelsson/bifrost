package no.vegvesen.dia.bifrost.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Autowired
    private BuildProperties buildProperties;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("S3gateway API")
                        .version(buildProperties.getVersion())
                        .description("REST API in front of S3 that stores images")
                        .license(new License().identifier("MIT-0").name("MIT No Attribution License").url("https://en.wikipedia.org/wiki/MIT_License"))
                );
    }
}
