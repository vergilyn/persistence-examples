package com.vergilyn.examples.jpa.basic.service.impl;

import java.time.LocalDateTime;

import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CrudBasicRepository;
import com.vergilyn.examples.jpa.basic.service.JpaBasicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaBasicServiceImpl implements JpaBasicService {
	@Autowired
	private CrudBasicRepository repository;

	@Override
	public JpaBasicEntity getByJpa(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public JpaBasicEntity getByJdbcTemplate(Long id) {
		return repository.findByJdbcTemplate(id);
	}

	@Override
	public JpaBasicEntity getByNamedTemplate(Long id) {
		return repository.findByNamedTemplate(id);
	}

	@Override
	@Transactional
	public JpaBasicEntity updateByJpa(Long id, String name) {
		JpaBasicEntity entity = getByJpa(id);

		entity.setName(name);
		entity.setModifyTime(LocalDateTime.now());

		entity = repository.save(entity);

		return entity;
	}

	@Override
	@Transactional
	public int updateByJdbcTemplate(Long id, String name) {
		return repository.updateByJdbcTemplate(id, name);
	}

	@Override
	@Transactional
	public int updateByNamedTemplate(Long id, String name) {
		return repository.updateByNamedTemplate(id, name);
	}
}
