package com.vergilyn.examples.mybatis.cache;

import java.io.Serializable;
import java.sql.SQLException;

import com.vergilyn.examples.mybatis.cache.entity.MybatisCacheEntity;
import com.vergilyn.examples.mybatis.cache.mapper.MybatisCacheMapper;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cache.TransactionalCacheManager;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.TransactionalCache;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * mybatis的 二级缓存 与 transaction。
 *
 * @author vergilyn
 * @since 2021-02-23
 */
@SpringBootTest(classes = MybatisCache2ndApplication.class,
		properties = {"spring.profiles.active=datasource,mybatis",
				"spring.datasource.druid.default-auto-commit=true"})
public class TransactionAnd2ndCacheTest extends AbstractMybatisCache2ndTest {

	/**
	 *
	 * 结论：
	 *   同一个SqlSession 或 不同SqlSession，都必须显示调用 {@linkplain DefaultSqlSession#commit()}，
	 *   才可以使用 2nd-cache。
	 *
	 * @see Meituan2ndTest#testCacheWithCommitOrClose()
	 * @see Meituan2ndTest#testCacheWithoutCommitOrClose()
	 *
	 */
	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	public void testCacheImplicitAutocommit(boolean isExplicitCommit) throws SQLException {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(true);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(true);

		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);

		MybatisCacheEntity withCache_11 = mapper_1.findWithCache(ID);
		System.out.println("mapper_1 read[1] >>>> use db, " + withCache_11);

		MybatisCacheEntity withCache_12 = mapper_1.findWithCache(ID);
		System.out.println("mapper_1 read[2] >>>> use 2nd-cache, " + withCache_12);

		/**
		 * 其实是因为 1st-cache，并不是 2nd-cache。
		 *
		 * {@link CachingExecutor#query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)} 中
		 * {@link TransactionalCacheManager#getObject(Cache, CacheKey)} 实际返回 null，
		 *
		 * 然后`delegate.query(...)` {@link BaseExecutor#query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)}
		 * 中使用 1st-cache 返回entity。
		 *
		 * 所以 `withCache_11 == withCache_12`。
		 */
		Assertions.assertTrue(withCache_11 == withCache_12);

		if (isExplicitCommit){
			/**
			 * autocommit 不会触发以下调用链，所以必须<b>显示调用`commit()`</b>！
			 *
			 * {@linkplain DefaultSqlSession#commit(boolean)}
			 *   -> {@linkplain CachingExecutor#commit(boolean)}
			 *     -> {@linkplain BaseExecutor#commit(boolean)}
			 *     -> {@linkplain TransactionalCacheManager#commit()}: 核心
			 */
			sqlSession_1.commit();
		}

		/**
		 * use 2nd-cache:
		 *   {@link DefaultSqlSession#selectOne(String, Object)}
		 *     -> {@link CachingExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)}
		 *       -> {@link TransactionalCacheManager#getObject(Cache, CacheKey)}
		 *         -> {@link SerializedCache#getObject(Object)}
		 */
		MybatisCacheEntity withCache_21 = mapper_2.findWithCache(ID);
		System.out.println("mapper_2 read[1] >>>> " + withCache_21);

		if (isExplicitCommit){
			/**
			 * `withCache_12 != withCache_21` reason:
			 * 当`sqlSession_1.commit()`时，会依次调用
			 *  {@link TransactionalCacheManager#commit()}
			 *  -> {@link TransactionalCache#commit()}
			 *  -> {@link TransactionalCache#flushPendingEntries()}
			 *  -> {@link SerializedCache#putObject(Object, Object)} 此时 `value == withCache_12`。
			 *    -> {@link SerializedCache#serialize(Serializable)} 将 `value == withCache_12` 序列化成 `byte[]`保存到2nd-cache。
			 *
			 * 然后在 {@link TransactionalCache#getObject(Object)}，
			 * 通过 {@link SerializedCache#deserialize(byte[])} 将 `byte[]`反序列化成 value。
			 *
			 * 所以，导致 `withCache_12 != withCache_21`，但实际`withCache_21`是通过 2nd-cache 获取。
			 */
			Assertions.assertTrue(withCache_12 == withCache_21);
		}else {
			Assertions.assertTrue(withCache_12 != withCache_21);
		}
	}
}
