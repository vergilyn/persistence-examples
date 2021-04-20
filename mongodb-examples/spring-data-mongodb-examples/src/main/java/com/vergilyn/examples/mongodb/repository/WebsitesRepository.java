package com.vergilyn.examples.mongodb.repository;

import com.vergilyn.examples.mongodb.document.WebsitesDoc;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author vergilyn
 * @since 2021-04-16
 */
public interface WebsitesRepository extends MongoRepository<WebsitesDoc, String> {

}
