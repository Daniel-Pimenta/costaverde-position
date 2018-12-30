package com.dotcom.costaverde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dotcom.costaverde.service.ThreadService;

@SpringBootApplication
public class CostaverdePositionApplication {

	public static void main(String[] args) {
		System.out.println("CostaverdePositionApplication.main("+args[0]+")");
		SpringApplication.run(CostaverdePositionApplication.class, args);
	}

}

