package com.vergilyn.examples.jpa.basic;

import com.vergilyn.examples.jpa.AbstractJpaBasicApplicationTest;
import com.vergilyn.examples.jpa.basic.entity.numeric.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CrudBasicRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.collections.Lists;

/**
 *
 * @author vergilyn
 * @since 2021-02-26
 *
 * @see JpaSoftDeleteCrudTest#softDelete()
 * @see JpaSoftDeleteCrudTest#softDeleteAll()
 */
public class DeleteEntityTest extends AbstractJpaBasicApplicationTest {

	@Autowired
	private CrudBasicRepository repository;

	/**
	 * @see JpaSoftDeleteCrudTest#softDelete()
	 */
	@Test
	public void delete(){
		/* LOG:
		 *   Hibernate: select * from jpa_basic where id=?
		 *   Hibernate: delete from jpa_basic where id=?
		 */
		JpaBasicEntity e1 = JpaBasicEntity.build(null);
		e1 = repository.save(e1);
		repository.delete(e1);

		/* LOG:
		 *   Hibernate: select * from jpa_basic where id=?
		 *   Hibernate: delete from jpa_basic where id=?
		 */
		JpaBasicEntity e2 = JpaBasicEntity.build(null);
		e2 = repository.save(e2);
		repository.deleteById(e2.getId());
	}

	/**
	 * @see JpaSoftDeleteCrudTest#softDeleteAll()
	 */
	@Test
	public void deleteAll(){
		JpaBasicEntity e1 = JpaBasicEntity.build(null);
		JpaBasicEntity e2 = JpaBasicEntity.build(null);
		JpaBasicEntity e3 = JpaBasicEntity.build(null);

		repository.saveAll(Lists.newArrayList(e1, e2, e3));

		/*
		 * 打印LOG:
		 *   Hibernate: select * from jpa_basic
		 *   Hibernate: delete from jpa_basic where id=?
		 *   Hibernate: delete from jpa_basic where id=?
		 *   Hibernate: delete from jpa_basic where id=?
		 *   Hibernate: delete from jpa_basic where id=?
		 *
		 * mysql-log:
		 *   SET autocommit=0;
		 *   select * from jpa_basic;
		 *   delete from jpa_basic where id=1;
         *   delete from jpa_basic where id=4;
         *   delete from jpa_basic where id=5;
         *   delete from jpa_basic where id=6;
		 *   commit;
		 */
		repository.deleteAll();
	}
}
