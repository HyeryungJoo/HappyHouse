package com.ssafy.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication
public class HappyhouseBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyhouseBeApplication.class, args);
	}

}
