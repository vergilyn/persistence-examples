package com.vergilyn.examples.shardingsphere.service;

import com.vergilyn.examples.shardingsphere.ShardingSphereMybatisPlusApplicationTest;
import com.vergilyn.examples.shardingsphere.entity.ShardingSphereMonthEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

class ShardingSphereMonthServiceTest extends ShardingSphereMybatisPlusApplicationTest {

	@Autowired
	private ShardingSphereMonthService shardingSphereMonthService;

	@Test
	public void test(){
		ShardingSphereMonthEntity entity = new ShardingSphereMonthEntity();
		entity.setId(null);
		entity.setCreateTime(LocalDateTime.now());
		entity.setName(entity.getCreateTime().toString());

		shardingSphereMonthService.save(entity);
	}
}