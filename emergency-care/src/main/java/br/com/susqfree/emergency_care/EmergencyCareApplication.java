package br.com.susqfree.emergency_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmergencyCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyCareApplication.class, args);
	}

}
