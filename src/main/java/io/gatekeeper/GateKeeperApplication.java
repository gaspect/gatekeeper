package io.gatekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class GateKeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateKeeperApplication.class, args);
	}

}
