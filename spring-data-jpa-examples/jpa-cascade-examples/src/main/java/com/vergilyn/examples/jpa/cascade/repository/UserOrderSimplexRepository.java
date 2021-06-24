package com.vergilyn.examples.jpa.cascade.repository;

import java.util.List;

import com.vergilyn.examples.jpa.cascade.entity.UserOrderSimplexEntity;

import org.springframework.data.repository.CrudRepository;

public interface UserOrderSimplexRepository extends CrudRepository<UserOrderSimplexEntity, Long> {

	List<UserOrderSimplexEntity> findByUserId(Long userId);
}
