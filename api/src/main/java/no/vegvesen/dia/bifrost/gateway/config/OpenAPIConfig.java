package no.vegvesen.dia.bifrost.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI within the Bifrost API.
 * It is responsible for setting up the OpenAPI documentation for the application.
 * The class is annotated with @Configuration, indicating to Spring that it contains
 * beans and other components to be used for configuration.
 */
@Configuration
public class OpenAPIConfig {

    // Automatically injected by Spring to access build properties (like version) of the application.
    @Autowired
    private BuildProperties buildProperties;

    /**
     * Bean that defines the custom configuration for the OpenAPI documentation.
     * This configuration includes the API title, version, description, and license.
     *
     * @return A custom OpenAPI object with the defined configuration.
     */
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Bifrost API") // The title of the API
                        .version(buildProperties.getVersion()) // The version of the API from build properties
                        .description("REST API providing an interface to S3/Kafka for storing unstructured and semi-unstructured data. It enables efficient data retrieval and management, offering a comprehensive solution for various data storage needs.")
                        //.termsOfService("https://example.com/terms") // TODO: Include a link to the terms of service if available
                        //.contact(new Contact().email("support@example.com").name("Support Team").url("https://example.com/support")) // Contact information for API support
                        .license(new License().identifier("MIT-0").name("MIT No Attribution License").url("https://en.wikipedia.org/wiki/MIT_License")) // License details
                );
    }
}
