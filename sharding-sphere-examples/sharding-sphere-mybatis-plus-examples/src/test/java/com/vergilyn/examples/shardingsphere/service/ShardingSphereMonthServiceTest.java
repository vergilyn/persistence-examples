package com.vergilyn.examples.shardingsphere.service;

import com.vergilyn.examples.shardingsphere.ShardingSphereMybatisPlusApplicationTest;
import com.vergilyn.examples.shardingsphere.entity.ShardingSphereMonthEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

class ShardingSphereMonthServiceTest extends ShardingSphereMybatisPlusApplicationTest {

	@Autowired
	private ShardingSphereMonthService shardingSphereMonthService;

	/**
	 * <pre>
	 * ### The error occurred while setting parameters
	 * ### SQL: INSERT INTO sharding_sphere_month  ( id, create_time, name )  VALUES  ( ?, ?, ? )
	 * ### Cause: java.lang.StringIndexOutOfBoundsException: String index out of range: 25
	 * 	at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:30)
	 * 	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:196)
	 * 	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:181)
	 * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	 * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	 * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	 * 	at java.lang.reflect.Method.invoke(Method.java:498)
	 * 	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:427)
	 * 	... 77 more
	 * Caused by: java.lang.StringIndexOutOfBoundsException: String index out of range: 25
	 * 	at java.lang.String.substring(String.java:1963)
	 * 	at org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm.parseDateTime(IntervalShardingAlgorithm.java:155)
	 * 	at org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm.hasIntersection(IntervalShardingAlgorithm.java:146)
	 * 	at org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm.doSharding(IntervalShardingAlgorithm.java:137)
	 * </pre>
	 */
	@Test
	public void test(){
		ShardingSphereMonthEntity entity = new ShardingSphereMonthEntity();
		entity.setId(null);
		entity.setCreateTime(LocalDateTime.now());
		entity.setName(entity.getCreateTime().toString());

		shardingSphereMonthService.save(entity);
	}
}