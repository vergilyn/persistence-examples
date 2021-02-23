package com.vergilyn.examples.mybatis.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author vergilyn
 * @since 2021-02-23
 */
@SpringBootApplication
@EnableTransactionManagement
public class MybatisCache2ndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisCache2ndApplication.class, args);
	}
}

