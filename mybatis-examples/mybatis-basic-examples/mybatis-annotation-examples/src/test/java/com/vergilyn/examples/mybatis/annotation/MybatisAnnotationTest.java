package com.vergilyn.examples.mybatis.annotation;

import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.mybatis.annotation.entity.MybatisAnnotationEntity;
import com.vergilyn.examples.mybatis.annotation.mapper.MybatisAnnotationMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MybatisAnnotationTest extends AbstractMybatisAnnotationTest{
	@Autowired
	private MybatisAnnotationMapper mybatisAnnotationMapper;

	private static String _name = "mybatis-annotation";
	private static Integer _id = null;

	@Test
	@Order(1)
	public void insert(){
		MybatisAnnotationEntity entity = new MybatisAnnotationEntity();
		entity.setName(_name);
		entity.setIsDeleted(false);
		entity.setCreateTime(LocalDateTime.now());
		entity.setEnumField("enum");

		mybatisAnnotationMapper.insert(entity);

		_id = entity.getId();

		Assertions.assertNotNull(_id);
		System.out.println("insert >>>> _id: " + _id);
	}

	@Test
	@Order(2)
	public void query(){
		MybatisAnnotationEntity entity = mybatisAnnotationMapper.findById(_id);

		Assertions.assertTrue(_name.equalsIgnoreCase(entity.getName()));

		System.out.println("query >>>> entity: " + JSON.toJSONString(entity));
	}

	@Test
	@Order(3)
	public void delete(){
		int row = mybatisAnnotationMapper.delete(_id);

		Assertions.assertEquals(1, row);

		System.out.println("delete >>>> row: " + row);
	}
}
