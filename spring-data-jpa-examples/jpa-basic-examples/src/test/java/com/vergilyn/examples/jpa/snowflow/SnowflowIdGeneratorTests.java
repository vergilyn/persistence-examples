package com.vergilyn.examples.jpa.snowflow;

import java.time.LocalTime;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.jpa.AbstractJpaBasicApplicationTest;
import com.vergilyn.examples.jpa.basic.entity.snowflow.JpaSnowflowIdEntity;
import com.vergilyn.examples.jpa.basic.repository.JpaSnowflowIdRepository;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SnowflowIdGeneratorTests extends AbstractJpaBasicApplicationTest {

	@Autowired
	JpaSnowflowIdRepository repository;

	@RepeatedTest(4)
	public void insert(){
		JpaSnowflowIdEntity entity = new JpaSnowflowIdEntity("auto-generator-snowflow-id");

		entity = repository.save(entity);

		System.out.println(JSON.toJSONString(entity, true));
	}

	/**
	 * not-exist:
	 * <pre>
	 *   Hibernate: select * from jpa_snowflow_id s where s.id=?
	 *   Hibernate: insert into jpa_snowflow_id (name, id) values (?, ?)
	 * </pre>
	 *
	 * exist:
	 * <pre>
	 *   Hibernate: select * from jpa_snowflow_id s where s.id=?
	 *   Hibernate: update jpa_snowflow_id set name=? where id=?
	 * </pre>
	 */
	@Test
	public void manualId(){
		JpaSnowflowIdEntity entity = new JpaSnowflowIdEntity("409839", "manual-input-id-" + LocalTime.now().toString());

		entity = repository.save(entity);

		System.out.println(JSON.toJSONString(entity, true));
	}
}
