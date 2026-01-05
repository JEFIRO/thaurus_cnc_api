package com.jefiro.thaurus_cnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ThaurusCncApplication {

	public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bahia"));
        SpringApplication.run(ThaurusCncApplication.class, args);
	}

}
