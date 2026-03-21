package com.palpita.palpita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PalpitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalpitaApplication.class, args);
	}
}
