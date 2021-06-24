package com.vergilyn.examples.jpa.basic;

import com.vergilyn.examples.jpa.AbstractJpaBasicApplicationTest;
import com.vergilyn.examples.jpa.basic.entity.numeric.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CrudBasicRepository;
import com.vergilyn.examples.jpa.basic.repository.PagingBasicRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FindEntityTest extends AbstractJpaBasicApplicationTest {
	@Autowired
	private CrudBasicRepository repository;

	@Autowired
	private PagingBasicRepository pagingRepository;

	private final Long _id = 1L;

	@BeforeEach
	public void beforeEach(){
		truncateTable(TABLE_NAME);
		repository.save(JpaBasicEntity.build(_id));
	}

	@Test
	public void find(){

		JpaBasicEntity jpa = repository.findById(_id).get();
		System.out.println("jpa >>>> " + jpa);

		JpaBasicEntity jdbc = repository.findByJdbcTemplate(_id);
		System.out.println("jdbcTemplate >>>> " + jdbc);

		JpaBasicEntity named = repository.findByNamedTemplate(_id);
		System.out.println("namedTemplate >>>> " + named);
	}
}
