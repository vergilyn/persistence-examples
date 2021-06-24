package com.vergilyn.examples.jpa.cascade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author vergilyn
 * @since 2021-06-22
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaCascadeApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(JpaCascadeApplication.class);

		ConfigurableApplicationContext context = application.run(args);
	}
}
