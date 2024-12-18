package com.project.flightManagement;

import com.project.flightManagement.Payload.ResponseData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightManagementApplication.class, args);


	}

}
