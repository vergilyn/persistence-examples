package com.vergilyn.examples.mybatis.cache;

import java.util.concurrent.atomic.AtomicInteger;

import com.vergilyn.examples.mybatis.cache.mapper.MybatisCacheMapper;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;

/**
 * cache-key: {@linkplain BaseExecutor#createCacheKey(MappedStatement, Object, RowBounds, BoundSql)} <br/>
 * query local-cache:
 *   {@linkplain BaseExecutor#query(MappedStatement, Object, RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, BoundSql)}
 *
 * <p>
 * invoke {@linkplain BaseExecutor#clearLocalCache()}:
 * <pre>
 *   update(DML) & auto-commit: {@linkplain BaseExecutor#update(MappedStatement, Object)}
 *   manual-commit: {@linkplain BaseExecutor#commit(boolean)}
 *   rollback: {@linkplain BaseExecutor#rollback(boolean)}
 *   query flush-cache: {@linkplain BaseExecutor#query(MappedStatement, Object, RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, BoundSql)}
 *   manual-clearCache: {@linkplain DefaultSqlSession#clearCache()}
 *   LocalCacheScope: {@linkplain Configuration#setLocalCacheScope(LocalCacheScope)}
 * </pre>
 *
 * @author vergilyn
 * @since 2021-02-20
 */
public class ClearLocalCacheTest extends AbstractMybatisCacheFirstTest{

	@Test
	public void clearLocalCache() {
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		MybatisCacheMapper mapper = sqlSession.getMapper(MybatisCacheMapper.class);
		AtomicInteger index = new AtomicInteger(0);

		printFindById(mapper, index);
		printFindById(mapper, index);

		/**
		 * 只要当前SqlSession 执行任何DML，都会调用：{@linkplain BaseExecutor#update(MappedStatement, Object)}
		 */
		System.out.println("DML delete(...) >>> " + mapper.delete(ID - 1));

		printFindById(mapper, index);
		printFindById(mapper, index);

		/**
		 * {@linkplain DefaultSqlSession#rollback()} -> {@linkplain BaseExecutor#rollback(boolean)}
 		 */
		sqlSession.rollback();
		System.out.println("rollback(...) >>> ");

		printFindById(mapper, index);
		printFindById(mapper, index);

		/**
		 * {@linkplain DefaultSqlSession#commit()} -> {@linkplain BaseExecutor#commit(boolean)}
		 */
		sqlSession.commit();
		System.out.println("manual-commit(...) >>> ");

		printFindById(mapper, index);
		printFindById(mapper, index);

		/**
		 * manual invoke `clearLocalCache()`
		 */
		sqlSession.clearCache();
		System.out.println("manual-clearCache(...) >>> ");

		printFindById(mapper, index);
		printFindById(mapper, index);

		/**
		 * TODO 2021-02-20
		 * flush-cache: {@linkplain org.apache.ibatis.annotations.Options#flushCache()}
		 */

		sqlSession.close();
	}

	@Test
	public void localCacheScope(){
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		/**
		 * see: {@linkplain BaseExecutor#query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)}
		 */
		sqlSession.getConfiguration().setLocalCacheScope(LocalCacheScope.STATEMENT);

		MybatisCacheMapper mapper = sqlSession.getMapper(MybatisCacheMapper.class);
		AtomicInteger index = new AtomicInteger(0);

		printFindById(mapper, index);
		printFindById(mapper, index);
	}

	private void printFindById(MybatisCacheMapper mapper, AtomicInteger index){
		System.out.printf("[%d]findById(...) >>> %s \n", index.incrementAndGet(), mapper.findById(ID));
	}
}
