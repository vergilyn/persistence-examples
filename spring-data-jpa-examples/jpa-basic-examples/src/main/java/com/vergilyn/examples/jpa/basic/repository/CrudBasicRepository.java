package com.vergilyn.examples.jpa.basic.repository;

import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author vergilyn
 * @since 2021-02-26
 *
 * @see org.springframework.data.repository.CrudRepository
 * @see PagingAndSortingRepository
 */
public interface CrudBasicRepository extends CrudRepository<JpaBasicEntity, Long>, CustomJpaBasicRepository {

}
