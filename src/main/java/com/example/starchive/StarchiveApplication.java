package com.example.starchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling //enable scheduling
public class StarchiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarchiveApplication.class, args);
	}

}
