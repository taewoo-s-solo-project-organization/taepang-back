package com.example.taepang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // 임시 security 제외 
public class TaepangApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaepangApplication.class, args);
	}

}
