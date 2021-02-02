package com.vergilyn.examples.mybatis.plus;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;
import com.vergilyn.examples.mybatis.plus.mapper.MybatisPlusMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@SpringBootTest(classes = MybatisPlusApplication.class, properties = "spring.profiles.active=datasource,mybatis")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MybatisPlusTest {

	@Resource
	private MybatisPlusMapper mybatisPlusMapper;

	private static String _name = "mybatis-plus";
	private static Long _id = null;

	@Test
	@Order(1)
	public void insert(){
		MybatisPlusEntity entity = new MybatisPlusEntity();
		entity.setName(_name);
		// entity.setNewField("new field...");

		mybatisPlusMapper.insert(entity);

		_id = entity.getId();

		Assertions.assertNotNull(_id);
		System.out.println("insert >>>> _id: " + _id);
	}

	@Test
	@Order(2)
	public void query(){
		MybatisPlusEntity entity = mybatisPlusMapper.selectById(_id);

		Assertions.assertTrue(_name.equalsIgnoreCase(entity.getName()));

		System.out.println("query >>>> entity: " + JSON.toJSONString(entity));
	}

	@Test
	@Order(3)
	public void delete(){
		int row = mybatisPlusMapper.deleteById(_id);

		Assertions.assertEquals(1, row);

		System.out.println("delete >>>> row: " + row);
	}
}
