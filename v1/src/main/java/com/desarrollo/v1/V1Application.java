package com.desarrollo.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.desarrollo.v1.service.UserService;

@SpringBootApplication
public class V1Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(V1Application.class, args);
		
		// Crear usuario admin por defecto
		UserService userService = context.getBean(UserService.class);
		userService.crearAdminPorDefecto();
		
		System.out.println("Hola Mundo Spring Boot");
	}

}
