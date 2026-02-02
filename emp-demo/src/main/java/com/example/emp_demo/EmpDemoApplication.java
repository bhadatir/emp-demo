package com.example.emp_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpDemoApplication.class, args);
	}

}
