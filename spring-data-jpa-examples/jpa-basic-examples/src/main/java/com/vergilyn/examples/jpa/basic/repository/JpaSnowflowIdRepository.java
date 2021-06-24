package com.vergilyn.examples.jpa.basic.repository;

import com.vergilyn.examples.jpa.basic.entity.snowflow.JpaSnowflowIdEntity;

import org.springframework.data.repository.CrudRepository;

public interface JpaSnowflowIdRepository extends CrudRepository<JpaSnowflowIdEntity, String> {

}
