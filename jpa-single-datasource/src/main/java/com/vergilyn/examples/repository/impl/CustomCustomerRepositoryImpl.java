package com.vergilyn.examples.repository.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.CustomCustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Customer getByJdbc(Long customerId) {
        String sql = "select * from customer where customer_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Customer.class), customerId);
    }

    @Override
    public Customer getByNamed(Long customerId) {
        String sql = "select * from customer where customer_id = :customerId";
        Map<String, Object> params = Maps.newHashMap();
        params.put("customerId", customerId);

        return namedJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Customer.class));
    }

    @Override
    public int updateByJdbc(Long customerId, String email) {
        String sql = "update customer set email = ? where customer_id = ?";
        return jdbcTemplate.update(sql, email, customerId);
    }

    @Override
    public int updateByNamed(Long customerId, String email) {
        String sql = "update customer set email = :email where customer_id = :customerId";
        Map<String, Object> params = Maps.newHashMap();
        params.put("email", email);
        params.put("customerId", customerId);

        return namedJdbcTemplate.update(sql, params);
    }
}
