package com.vergilyn.examples.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author vergilyn
 * @since 2021-04-16
 */
@SpringBootApplication
public class SpringDataMongodbApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringDataMongodbApplication.class);

		ConfigurableApplicationContext context = application.run(args);
	}
}
