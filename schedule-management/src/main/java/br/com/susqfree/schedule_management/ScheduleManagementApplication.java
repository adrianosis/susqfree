package br.com.susqfree.schedule_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ScheduleManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleManagementApplication.class, args);
	}

}
