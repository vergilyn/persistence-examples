package com.vergilyn.examples.jpa.basic.repository.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CustomJpaBasicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity.TABLE_NAME;

public class CustomJpaBasicRepositoryImpl implements CustomJpaBasicRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public JpaBasicEntity findByJdbcTemplate(Long id) {
		String sql = "SELECT * FROM " + TABLE_NAME  + " WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(JpaBasicEntity.class), id);
	}

	@Override
	public JpaBasicEntity findByNamedTemplate(Long id) {
		String sql = "select * from " + TABLE_NAME + " where id = :id";
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);

		return namedJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(JpaBasicEntity.class));
	}

	@Override
	public int updateByJdbcTemplate(Long id, String name) {
		String sql = "UPDATE " + TABLE_NAME + " SET `name` = ? where id = ?";
		return jdbcTemplate.update(sql, name, id);
	}

	@Override
	public int updateByNamedTemplate(Long id, String name) {
		String sql = "UPDATE " + TABLE_NAME + " SET `name` = :name where id = :id";
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("name", name);

		return namedJdbcTemplate.update(sql, params);
	}
}
