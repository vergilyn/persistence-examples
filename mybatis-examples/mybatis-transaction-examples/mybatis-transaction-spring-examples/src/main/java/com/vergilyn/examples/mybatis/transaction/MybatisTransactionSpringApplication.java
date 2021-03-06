package com.vergilyn.examples.mybatis.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author vergilyn
 * @since 2021-02-20
 */
@SpringBootApplication
@EnableTransactionManagement
public class MybatisTransactionSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisTransactionSpringApplication.class, args);
	}
}
