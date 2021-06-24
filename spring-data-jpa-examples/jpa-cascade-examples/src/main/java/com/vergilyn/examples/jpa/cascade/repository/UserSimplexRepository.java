package com.vergilyn.examples.jpa.cascade.repository;

import com.vergilyn.examples.jpa.cascade.entity.UserSimplexEntity;

import org.springframework.data.repository.CrudRepository;

public interface UserSimplexRepository extends CrudRepository<UserSimplexEntity, Long> {
}
