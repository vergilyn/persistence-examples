package com.vergilyn.examples.mybatis.usage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author vergilyn
 * @since 2021-04-13
 */
@SpringBootApplication
@MapperScan(basePackages = "com.vergilyn.examples.mybatis.usage.mapper")
public class MybatisUsageApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MybatisUsageApplication.class);

		ConfigurableApplicationContext context = application.run(args);

	}
}
