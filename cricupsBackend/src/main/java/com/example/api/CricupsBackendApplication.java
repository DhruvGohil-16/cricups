package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CricupsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CricupsBackendApplication.class, args);

//		String baseUrl = "https://api.cricapi.com/v1";
//		String url = "/players?apikey=839254ab-5968-408d-a5b0-ac8a1d9e4c71&offset=0";
//		WebClient.Builder webClientBuilder = (WebClient.Builder) WebClient.builder()
//				.baseUrl(baseUrl)
//				.defaultHeader("apikey", "839254ab-5968-408d-a5b0-ac8a1d9e4c71"); // Set timeout if needed
//
//		WebClient webClient = webClientBuilder.build();
//
//		String player = String.valueOf(webClient.get()
//				.uri(url)
//				.retrieve()
//				.bodyToMono(String.class)
//				.block());
//
//		System.out.println(player);
	}

}
