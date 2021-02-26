package com.vergilyn.examples.jpa.basic.repository;

import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author vergilyn
 * @since 2021-02-26
 *
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/2.4.5/reference/html/#specifications">JpaSpecificationExecutor</a>
 */
public interface PagingBasicRepository extends PagingAndSortingRepository<JpaBasicEntity, Long>,
		JpaSpecificationExecutor<JpaBasicEntity> {

}
