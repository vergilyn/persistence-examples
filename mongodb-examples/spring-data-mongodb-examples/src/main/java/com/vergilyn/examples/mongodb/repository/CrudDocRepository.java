package com.vergilyn.examples.mongodb.repository;

import com.vergilyn.examples.mongodb.document.CrudDoc;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author vergilyn
 * @since 2021-04-16
 */
public interface CrudDocRepository extends MongoRepository<CrudDoc, String> {

}
