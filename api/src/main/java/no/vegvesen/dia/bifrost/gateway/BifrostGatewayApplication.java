package no.vegvesen.dia.bifrost.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"no.vegvesen.dia.bifrost.gateway.*", "no.vegvesen.dia.bifrost.core.services", "no.vegvesen.dia.bifrost.core.cryptography"})
public class BifrostGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BifrostGatewayApplication.class, args);
	}

}
