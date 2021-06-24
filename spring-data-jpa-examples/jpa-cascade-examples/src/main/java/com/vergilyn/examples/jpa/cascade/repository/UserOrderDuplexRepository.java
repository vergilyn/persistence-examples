package com.vergilyn.examples.jpa.cascade.repository;

import java.util.List;

import com.vergilyn.examples.jpa.cascade.entity.UserOrderDuplexEntity;

import org.springframework.data.repository.CrudRepository;

public interface UserOrderDuplexRepository extends CrudRepository<UserOrderDuplexEntity, Long> {

	List<UserOrderDuplexEntity> findByUserId(Long userId);
}
