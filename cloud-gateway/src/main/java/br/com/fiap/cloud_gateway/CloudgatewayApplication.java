package br.com.fiap.cloud_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudgatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route( r -> r.path("/doctor-management/**").filters(f -> f.stripPrefix(1)).uri("lb://doctor-management"))
				.route( r -> r.path("/health-unit-management/**").filters(f -> f.stripPrefix(1)).uri("lb://health-unit-management"))
				.route( r -> r.path("/patient-management/**").filters(f -> f.stripPrefix(1)).uri("lb://patient-management"))
				.route( r -> r.path("/schedule-management/**").filters(f -> f.stripPrefix(1)).uri("lb://schedule-management"))
				.route( r -> r.path("/triage-service/**").filters(f -> f.stripPrefix(1)).uri("lb://triage-service"))
				.build();
	}

}
