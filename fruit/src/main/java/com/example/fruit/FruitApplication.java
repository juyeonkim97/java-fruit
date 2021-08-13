package com.example.fruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.fruit"})
public class FruitApplication {

	public static void main(String[] args) {
		SpringApplication.run(FruitApplication.class, args);
	}

}
