package com.vergilyn.examples.mybatis.cache;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisCache1stApplication.class, properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisCache1stTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;

}
