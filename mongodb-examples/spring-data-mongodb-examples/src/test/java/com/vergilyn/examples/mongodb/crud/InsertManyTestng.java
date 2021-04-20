package com.vergilyn.examples.mongodb.crud;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Stopwatch;
import com.vergilyn.examples.mongodb.AbstractMongodbTestng;
import com.vergilyn.examples.mongodb.document.BatchOperateDoc;
import com.vergilyn.examples.mongodb.repository.BatchOperateDocRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author vergilyn
 * @since 2021-04-19
 *
 * @see <a href="https://docs.mongodb.com/manual/core/bulk-write-operations/">Bulk Write Operations</a>
 */
public class InsertManyTestng extends AbstractMongodbTestng {

	@Autowired
	private BatchOperateDocRepository repository;

	private static final int INVOCATION_COUNT = 1;
	private final int _size = 100_000;
	private List<BatchOperateDoc> _docs;

	@BeforeClass
	public void beforeClass(){
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
	public void beforeMethod(){
		System.out.print("[vergilyn] >>>> #beforeMethod() BEFORE, delete-all \n");
		repository.deleteAll();
		System.out.print("[vergilyn] >>>> #beforeMethod() AFTER, delete-all \n");
	}

	/**
	 * <pre>
	 *   100_000      2s
	 *   1_000_000    15~16s
	 * </pre>
	 */
	@Test(singleThreaded = true, invocationCount = INVOCATION_COUNT)
	public void saveAll(){
		Stopwatch stopwatch = Stopwatch.createStarted();

		repository.saveAll(_docs);

		stopwatch.stop();

		System.out.printf("[vergilyn] >>>> #saveAll(), %s \n", stopwatch);
	}

	/**
	 * 略微有点提升
	 * <pre>
	 *   100_000      1.8s
	 *   1_000_000    14~15s
	 * </pre>
	 */
	@Test(singleThreaded = true, invocationCount = INVOCATION_COUNT)
	public void insertManyOrder(){
		Stopwatch stopwatch = Stopwatch.createStarted();

		mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, BatchOperateDoc.class)
				.insert(_docs).execute();

		stopwatch.stop();

		System.out.printf("[vergilyn] >>>> #insertManyOrder(), %s \n", stopwatch);
	}

	@Test(singleThreaded = true, invocationCount = INVOCATION_COUNT)
	public void insertManyUnorder(){
		Stopwatch stopwatch = Stopwatch.createStarted();

		mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, BatchOperateDoc.class)
				.insert(_docs).execute();

		stopwatch.stop();

		System.out.printf("[vergilyn] >>>> #insertManyUnorder(), %s \n", stopwatch);
	}
}
