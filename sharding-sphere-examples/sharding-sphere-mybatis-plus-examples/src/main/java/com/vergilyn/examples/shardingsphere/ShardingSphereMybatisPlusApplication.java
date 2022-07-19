package com.vergilyn.examples.shardingsphere;

import org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author vergilyn
 * @since 2022-05-27
 */
@SpringBootApplication(exclude = ShardingSphereAutoConfiguration.class)
@MapperScan("com.vergilyn.examples.shardingsphere.mapper")
public class ShardingSphereMybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ShardingSphereMybatisPlusApplication.class);

		ConfigurableApplicationContext context = application.run(args);

		System.out.println("started :" + context);
	}
}
