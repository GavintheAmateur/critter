package com.udacity.jdnd.course3.critter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
@EnableJpaRepositories("com.udacity.jdnd.course3.critter.repository")
@EnableTransactionManagement
@EnableJpaAuditing
public class CritterApplication {

	public static void main(String[] args) {

		SpringApplication.run(CritterApplication.class, args);
	}

}
