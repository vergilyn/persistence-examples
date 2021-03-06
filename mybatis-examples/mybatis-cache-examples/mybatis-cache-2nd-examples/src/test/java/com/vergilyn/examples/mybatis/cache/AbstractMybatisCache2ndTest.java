package com.vergilyn.examples.mybatis.cache;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisCache2ndApplication.class,
		properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisCache2ndTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;

}
