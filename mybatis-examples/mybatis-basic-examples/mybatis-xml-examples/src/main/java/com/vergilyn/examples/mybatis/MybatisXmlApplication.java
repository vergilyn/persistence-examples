package com.vergilyn.examples.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.vergilyn.examples.mybatis.dao"})
public class MybatisXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisXmlApplication.class, args);
	}
}
