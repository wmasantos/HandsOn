package br.com.handson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class HandsOnApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(HandsOnApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(HandsOnApplication.class);
	}

	@RequestMapping(value = "/")
	public String healthCheck(){
		return "API ROCK's - " + new Date().toString();
	}
}
