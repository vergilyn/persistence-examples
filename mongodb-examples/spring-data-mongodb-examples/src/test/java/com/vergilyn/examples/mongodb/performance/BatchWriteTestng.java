package com.vergilyn.examples.mongodb.performance;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.vergilyn.examples.mongodb.AbstractMongodbTestng;
import com.vergilyn.examples.mongodb.document.BatchOperateDoc;
import com.vergilyn.examples.mongodb.repository.BatchOperateDocRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * 100_000:
 * <pre>
 *   saveAll:               2.108 s
 *   insertAll:             1.248 s
 *   insertManyUnordered:   1.106 s
 *   insertManyOrdered:     1.227 s
 * </pre>
 *
 * 1_000_000:
 * <pre>
 *   saveAll:               19.05 s
 *   insertAll:             13.48 s
 *   insertManyUnordered:   12.14 s
 *   insertManyOrdered:     13.87 s
 * </pre>
 *
 * <p>
 * 1) `insertAll` 和 `insertManyOrdered` 几乎相同。 <br/>
 * 2) 不建议用`saveAll`，每条数据都会判断`isNew`，比较浪费。 <br/>
 * 3) `insertManyUnordered` 最优。 <br/>
 * </p>
 *
 * @author vergilyn
 * @since 2021-04-19
 *
 * @see <a href="https://docs.mongodb.com/manual/core/bulk-write-operations/">Bulk Write Operations</a>
 */
public class BatchWriteTestng extends AbstractMongodbTestng {

	private static final Map<String, Stopwatch> TEST_NAMES_WATCH = Maps.newHashMap();

	@Autowired
	private BatchOperateDocRepository repository;

	private List<BatchOperateDoc> _docs;

	@BeforeClass
	public void beforeClass(){
		int _size = 1_000_000;
		_docs = Stream.iterate(BatchOperateDoc.buildDefault(), doc -> {
					BatchOperateDoc domain = new BatchOperateDoc();
					BeanUtils.copyProperties(doc, domain);
					domain.setTInteger(doc.getTInteger() + 1);
					return domain;
				})
				.limit(_size)
				.collect(Collectors.toList());
	}

	@BeforeMethod
	public void beforeMethod(Method method){
		String testName = method.getName();

		System.out.printf("[vergilyn][BeforeMethod] >>>> %s, delete-all-BEFORE \n", testName);
		repository.deleteAll();
		System.out.printf("[vergilyn][BeforeMethod] >>>> %s, delete-all-AFTER \n", testName);

		TEST_NAMES_WATCH.put(testName, Stopwatch.createStarted());
	}

	@AfterMethod
	public void afterMethod(Method method){
		String testName = method.getName();
		TEST_NAMES_WATCH.get(testName).stop();
	}

	@AfterTest
	public void afterTest(){

		for (Map.Entry<String, Stopwatch> entry : TEST_NAMES_WATCH.entrySet()) {
			System.out.printf("%s:\t %s \n", entry.getKey(), entry.getValue().toString());
		}
	}

	/**
	 * {@linkplain SimpleMongoRepository#saveAll(Iterable)} 会先判断`isNew`，再`insertMany`。
	 */
	@Test(singleThreaded = true, priority = 1)
	public void saveAll(){
		repository.saveAll(_docs);
	}

	/**
	 * 会根据`entities#collectionName()`进行分组，然后在insertMany。
	 */
	@Test(singleThreaded = true, priority = 2)
	public void insertAll(){
		repository.insert(_docs);
	}

	@Test(singleThreaded = true, priority = 3)
	public void insertManyOrdered(){

		mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, BatchOperateDoc.class)
				.insert(_docs).execute();

	}

	@Test(singleThreaded = true, priority = 4)
	public void insertManyUnordered(){

		mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, BatchOperateDoc.class)
				.insert(_docs).execute();
	}
}
