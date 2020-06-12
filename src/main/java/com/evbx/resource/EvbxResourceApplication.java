package com.evbx.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EvbxResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvbxResourceApplication.class, args);
	}

}
