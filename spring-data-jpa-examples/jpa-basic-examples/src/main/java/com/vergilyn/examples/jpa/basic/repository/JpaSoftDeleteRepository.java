package com.vergilyn.examples.jpa.basic.repository;

import com.vergilyn.examples.jpa.basic.entity.numeric.JpaSoftDeleteEntity;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaSoftDeleteRepository extends PagingAndSortingRepository<JpaSoftDeleteEntity, Long>,
		JpaSpecificationExecutor<JpaSoftDeleteEntity> {

}
