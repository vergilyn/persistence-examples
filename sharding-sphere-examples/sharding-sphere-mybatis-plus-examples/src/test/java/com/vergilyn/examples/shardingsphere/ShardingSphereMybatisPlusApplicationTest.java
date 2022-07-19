package com.vergilyn.examples.shardingsphere;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShardingSphereMybatisPlusApplication.class,
		properties = {"spring.profiles.active=mybatis,datasource,shardingsphere-tables"})
public abstract class ShardingSphereMybatisPlusApplicationTest {

}