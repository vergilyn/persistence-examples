package com.vergilyn.examples.mongodb.crud;

import java.time.LocalDateTime;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.result.UpdateResult;
import com.vergilyn.examples.mongodb.AbstractMongodbTestng;
import com.vergilyn.examples.mongodb.document.CrudDoc;
import com.vergilyn.examples.mongodb.repository.CrudDocRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

/**
 *
 * @author vergilyn
 * @since 2021-04-16
 *
 * @see <a href="https://docs.mongodb.com/manual/crud/">MongoDB CRUD Operations</a>
 */
public class CrudApiTestng extends AbstractMongodbTestng {

	@Autowired
	private CrudDocRepository repository;

	private CrudDoc doc = CrudDoc.buildDefault();

	@Test
	public void delete(){
		repository.deleteAll();
	}

	@Test(dependsOnMethods = "delete")
	public void insert(){
		repository.insert(doc);
	}

	@Test(dependsOnMethods = {"delete", "insert"})
	public void update(){
		CrudDoc update = new CrudDoc();
		update.setId(doc.getId());
		update.setTInteger(2);

		// be equivalent to: delete-insert
		repository.save(update);
	}

	@Test(dependsOnMethods = {"delete", "insert"})
	public void incr(){

		Update update = new Update()
				.inc("tInteger", 2)
				.set("tLocalDateTime", LocalDateTime.now().plusDays(1));

		Criteria criteria = Criteria.where("_id").is(doc.getId());

		Query query = new Query(criteria);

		UpdateResult result = mongoTemplate.update(CrudDoc.class).matching(query).apply(update).all();

		System.out.printf("[vergilyn] >>>> matched-count: %d, modified-count: %d  \n",
					result.getModifiedCount(), result.getMatchedCount());

	}

	@AfterTest
	public void findAll(){
		List<CrudDoc> all = repository.findAll();

		System.out.println(JSON.toJSONString(all, PrettyFormat));

	}
}
