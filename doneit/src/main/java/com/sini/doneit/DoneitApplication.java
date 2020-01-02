package com.sini.doneit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DoneitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoneitApplication.class, args);
	}

}
