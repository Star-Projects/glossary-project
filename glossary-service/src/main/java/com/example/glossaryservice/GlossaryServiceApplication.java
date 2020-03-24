package com.example.glossaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GlossaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlossaryServiceApplication.class, args);
	}

}
