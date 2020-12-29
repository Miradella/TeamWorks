package com.example.TeamWork;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaAuditing
public class TeamWorkApplication {

	/*public static void main(String[] args) {
		SpringApplication.run(TeamWorkApplication.class, args);
	}*/

	public static void main(String[] args) {

		SpringApplication.run(TeamWorkApplication.class, args);
	}
/*
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	} */

}
