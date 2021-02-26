package com.vergilyn.examples.jpa.basic;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

@SpringBootTest(classes = JpaBasicApplication.class)
public abstract class AbstractJpaBasicApplicationTest {
	protected static final String TABLE_NAME = JpaBasicEntity.TABLE_NAME;

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected NamedParameterJdbcTemplate namedTemplate;

	protected void truncateTable(String tableName){
		// 清空数据
		jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
	}

	protected String toJSONString(Object obj){
		return JSON.toJSONString(obj, PrettyFormat);
	}
}
