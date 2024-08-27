package com.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MarketFreshApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketFreshApplication.class, args);
	}

}
