package com.sini.doneit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DoneitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoneitApplication.class, args);
	}

}
