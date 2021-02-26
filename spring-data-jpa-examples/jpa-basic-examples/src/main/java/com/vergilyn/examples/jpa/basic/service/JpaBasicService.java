package com.vergilyn.examples.jpa.basic.service;

import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;

public interface JpaBasicService {

	JpaBasicEntity getByJpa(Long id);

	JpaBasicEntity getByJdbcTemplate(Long id);

	JpaBasicEntity getByNamedTemplate(Long id);

	JpaBasicEntity updateByJpa(Long id, String name);

	int updateByJdbcTemplate(Long id, String name);

	int updateByNamedTemplate(Long id, String name);
}
