package com.ventrek.bookingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingPlatformBackendApplication {

	public static void main(String[] args) {
		System.setProperty("GOOGLE_APPLICATION_CREDENTIALS","/firebase.json");
		SpringApplication.run(BookingPlatformBackendApplication.class, args);
	}

}
