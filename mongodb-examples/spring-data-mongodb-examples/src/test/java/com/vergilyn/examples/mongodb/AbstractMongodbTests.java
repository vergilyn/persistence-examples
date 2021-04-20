package com.vergilyn.examples.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author vergilyn
 * @since 2021-04-16
 */
@SpringBootTest(classes = SpringDataMongodbApplication.class,
	properties = {"spring.profiles.active=mongodb"})
public abstract class AbstractMongodbTests {

	@Autowired
	protected MongoTemplate mongoTemplate;

}
