package br.com.fiap.triage_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TriageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriageServiceApplication.class, args);
	}

}
