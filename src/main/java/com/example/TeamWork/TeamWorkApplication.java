package com.example.TeamWork;


import com.example.TeamWork.Controller.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
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