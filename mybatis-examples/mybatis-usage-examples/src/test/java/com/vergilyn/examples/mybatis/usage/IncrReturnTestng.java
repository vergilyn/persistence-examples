package com.vergilyn.examples.mybatis.usage;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.vergilyn.examples.mybatis.usage.entity.MybatisCountersEntity;
import com.vergilyn.examples.mybatis.usage.mapper.MybatisCountersMapper;

import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

/**
 * 1. <a href="http://www.sqlines.com/mysql/how-to/select-update-single-statement-race-condition">
 *      MySQL How To Select and Update in Single Statement - Increment Counter avoiding Race Condition
 *    </a>
 * @author vergilyn
 * @since 2021-04-13
 */
public class IncrReturnTestng extends AbstractMybatisUsageApplicationTestng {
	@Autowired
	private MybatisCountersMapper countersMapper;

	private final Long _id = 1L;

	/**
	 * 最终mysql中的结果是正确的`counter = 10`，但是每次获取到的`entity.counter`不可预知的且可能重复。
	 */
	@SneakyThrows
	@Test
	public void afterQueryOutTrx(){
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		AtomicLong index = new AtomicLong(0);
		ThreadLocal<Long> threadLocal = new ThreadLocal<>();
		Map<Long, MybatisCountersEntity> log = Maps.newConcurrentMap();

		int invocationCount = 10;
		CountDownLatch latch = new CountDownLatch(invocationCount);
		for (int i = 0; i < invocationCount; i++) {
			threadPool.submit(() -> {
				threadLocal.set(index.getAndIncrement());

				// incr 与 selectById 不在同一个事务中
				countersMapper.incr(_id);
				MybatisCountersEntity entity = countersMapper.selectById(_id);

				log.put(threadLocal.get(), entity);
				System.out.printf("[%d] >>>> %s \n", threadLocal.get(), entity);

				latch.countDown();
			});
		}

		latch.await();

		System.out.println("======================= afterQueryOutTrx() log ============================");
		log.forEach((cache, entity) -> {
			System.out.printf("[%d] >>>> %s \n", cache, entity);
		});

		Assertions.assertThat(log.values().stream().mapToLong(MybatisCountersEntity::getCounter))
				// .containsExactlyInAnyOrder(Stream.iterate(1L, a -> ++a).limit(invocationCount).toArray(Long[]::new))
				.containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
	}

	/**
	 * `incr()` 和 `selectById()` 处在同一事务下。
	 * <p>
	 *   可以保证`entity.counter`不重复
	 */
	@SneakyThrows
	@Test
	public void afterQueryInTrx(){
		boolean defaultAutoCommit = druidDataSource.isDefaultAutoCommit();
		// 取消自动提交
		druidDataSource.setDefaultAutoCommit(false);

		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		AtomicLong index = new AtomicLong(0);
		ThreadLocal<Long> threadLocal = new ThreadLocal<>();
		Map<Long, MybatisCountersEntity> log = Maps.newConcurrentMap();

		int invocationCount = 10;
		CountDownLatch latch = new CountDownLatch(invocationCount);
		for (int i = 0; i < invocationCount; i++) {
			threadPool.submit(() -> {
				threadLocal.set(index.getAndIncrement());
				SqlSession sqlSession = null;
				try {
					// 实际上无法控制 auto-commit，所以需要修改 DruidDataSource.defaultAutoCommit
					sqlSession = sqlSessionFactory.openSession(false);
					MybatisCountersMapper mapper = sqlSession.getMapper(MybatisCountersMapper.class);

					// incr 与 selectById 不在同一个事务中
					mapper.incr(_id);
					MybatisCountersEntity entity = mapper.selectById(_id);

					sqlSession.commit(true);  // 手动提交

					log.put(threadLocal.get(), entity);
					System.out.printf("[%d] >>>> %s \n", threadLocal.get(), entity);
				}finally {
					latch.countDown();
					if (sqlSession != null) {
						sqlSession.close();
					}
				}
			});
		}

		latch.await();
		// reset-config
		druidDataSource.setDefaultAutoCommit(defaultAutoCommit);

		System.out.println("======================= afterQueryOutTrx() log ============================");
		log.forEach((cache, entity) -> {
			System.out.printf("[%d] >>>> %s \n", cache, entity);
		});

		Assertions.assertThat(log.values().stream().mapToLong(MybatisCountersEntity::getCounter))
				// .containsExactlyInAnyOrder(Stream.iterate(1L, a -> ++a).limit(invocationCount).toArray(Long[]::new))
				.containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
	}

}
