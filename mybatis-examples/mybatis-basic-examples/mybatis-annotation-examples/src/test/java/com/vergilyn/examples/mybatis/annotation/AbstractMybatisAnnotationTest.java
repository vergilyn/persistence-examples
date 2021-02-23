package com.vergilyn.examples.mybatis.annotation;

import com.vergilyn.examples.mybatis.annotation.mapper.MybatisAnnotationMapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisAnnotationApplication.class,
		properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisAnnotationTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired
	protected MybatisAnnotationMapper mapper;
}
