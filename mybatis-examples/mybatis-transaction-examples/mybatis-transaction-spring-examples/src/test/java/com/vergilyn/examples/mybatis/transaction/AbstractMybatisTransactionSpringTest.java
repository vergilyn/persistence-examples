package com.vergilyn.examples.mybatis.transaction;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisTransactionSpringApplication.class, properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisTransactionSpringTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
}
