package no.vegvesen.dia.bifrost.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * Bifrost Gateway Application.
 * <p>
 * This class defines the entry point for the Bifrost Gateway service, initializing the application context and
 * executing custom behaviors during startup. It's configured to scan specific packages for Spring components.
 * </p>
 *
 * @author andreas.erik.wilhelm.joelsson@vegvesen.no
 * @version 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "no.vegvesen.dia.bifrost.gateway.*",
        "no.vegvesen.dia.bifrost.core.config",
        "no.vegvesen.dia.bifrost.core"
})
public class BifrostGatewayApplication {

    /**
     * Main entry point for the application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BifrostGatewayApplication.class, args);
    }

    /**
     * Bean definition for command-line runner.
     * <p>
     * This method defines a command-line runner bean that lists all the Spring-managed beans in the application context
     * after startup. This is primarily used for debugging and inspection purposes.
     * </p>
     *
     * @param ctx ApplicationContext to be inspected
     * @return CommandLineRunner bean
     */
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
}
