package com.hyperit.flightadvisor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hyperit.flightadvisor.mapper")
public class FlightAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightAdvisorApplication.class, args);
	}

}
