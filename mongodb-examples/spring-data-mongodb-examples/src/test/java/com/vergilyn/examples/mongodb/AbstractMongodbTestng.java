package com.vergilyn.examples.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author vergilyn
 * @since 2021-04-19
 */
@SpringBootTest(classes = SpringDataMongodbApplication.class,
		properties = {"spring.profiles.active=mongodb"})
public abstract class AbstractMongodbTestng extends AbstractTestNGSpringContextTests {

	@Autowired
	protected MongoTemplate mongoTemplate;
}
