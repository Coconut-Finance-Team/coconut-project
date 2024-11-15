package com.coconut.stock_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StockAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockAppApplication.class, args);
	}
}
