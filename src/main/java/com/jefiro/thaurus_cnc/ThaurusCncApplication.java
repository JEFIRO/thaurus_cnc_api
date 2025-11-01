package com.jefiro.thaurus_cnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ThaurusCncApplication {

	public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bahia"));
        SpringApplication.run(ThaurusCncApplication.class, args);
	}

}
