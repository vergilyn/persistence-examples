package com.vergilyn.examples.mybatis.usage;

import javax.annotation.Resource;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = MybatisUsageApplication.class
		, properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisUsageApplicationTestng extends AbstractTestNGSpringContextTests implements
		InitializingBean {

	@Resource
	public SqlSessionFactory sqlSessionFactory;
	@Resource
	public DruidDataSource druidDataSource;

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}
