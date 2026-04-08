package com.example.AventureiroSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AventureiroSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(AventureiroSpringApplication.class, args);
	}
}
