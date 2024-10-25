package com.municipalidad.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.municipalidad.thymeleaf.controller", "com.municipalidad.service"})
@ComponentScan(basePackages = "com.municipalidad")
@EnableJpaRepositories(basePackages = "com.municipalidad.repository")
@EntityScan(basePackages = "com.municipalidad.model")  
public class SpringBootMunicipalidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMunicipalidadApplication.class, args);
	}

}
