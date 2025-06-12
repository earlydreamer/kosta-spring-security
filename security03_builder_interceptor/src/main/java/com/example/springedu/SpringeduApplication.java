package com.example.springedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringeduApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringeduApplication.class, args);
	}

}
