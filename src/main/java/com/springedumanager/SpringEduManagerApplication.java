package com.springedumanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada principal de la aplicacion Spring Edu Manager.
 */
@SpringBootApplication
public class SpringEduManagerApplication {

	/**
	 * Inicia el contexto de Spring Boot.
	 *
	 * @param args argumentos de arranque del proceso
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringEduManagerApplication.class, args);
	}

}
