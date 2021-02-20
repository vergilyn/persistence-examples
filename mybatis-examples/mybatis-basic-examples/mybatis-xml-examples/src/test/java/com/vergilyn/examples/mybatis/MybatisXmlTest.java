package com.vergilyn.examples.mybatis;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.mybatis.dao.MybatisXmlDao;
import com.vergilyn.examples.mybatis.entity.MybatisXmlEntity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisXmlApplication.class, properties = "spring.profiles.active=datasource,mybatis")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MybatisXmlTest {

	@Autowired
	private MybatisXmlDao mybatisXmlDao;

	private static String _name = "mybatis-xml";
	private static Integer _id = null;

	@Test
	@Order(1)
	public void insert(){
		MybatisXmlEntity entity = new MybatisXmlEntity();
		entity.setName(_name);

		mybatisXmlDao.insertSelective(entity);

		_id = entity.getId();

		Assertions.assertNotNull(_id);
		System.out.println("insert >>>> _id: " + _id);
	}

	@Test
	@Order(2)
	public void query(){
		MybatisXmlEntity entity = mybatisXmlDao.selectByPrimaryKey(_id);

		Assertions.assertTrue(_name.equalsIgnoreCase(entity.getName()));

		System.out.println("query >>>> entity: " + JSON.toJSONString(entity));
	}

	@Test
	@Order(3)
	public void delete(){
		int row = mybatisXmlDao.deleteByPrimaryKey(_id);

		Assertions.assertEquals(1, row);

		System.out.println("delete >>>> row: " + row);
	}
}
