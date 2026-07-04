package com.example.taepang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TaepangApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaepangApplication.class, args);
	}

}
