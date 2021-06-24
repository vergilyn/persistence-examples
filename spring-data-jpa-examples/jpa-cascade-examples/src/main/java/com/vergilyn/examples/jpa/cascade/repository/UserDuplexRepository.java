package com.vergilyn.examples.jpa.cascade.repository;

import com.vergilyn.examples.jpa.cascade.entity.UserDuplexEntity;

import org.springframework.data.repository.CrudRepository;

public interface UserDuplexRepository extends CrudRepository<UserDuplexEntity, Long> {

}
