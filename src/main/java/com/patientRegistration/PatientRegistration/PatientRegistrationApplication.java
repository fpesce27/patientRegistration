package com.patientRegistration.PatientRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PatientRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientRegistrationApplication.class, args);
	}

}
