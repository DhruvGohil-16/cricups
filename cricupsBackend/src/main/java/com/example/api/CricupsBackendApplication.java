package com.example.api;

import com.example.api.service.MatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CricupsBackendApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CricupsBackendApplication.class, args);

		MatchService matchService = context.getBean(MatchService.class);

//		matchService.updateMatches();
	}

}
