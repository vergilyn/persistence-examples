package com.vergilyn.examples.mybatis.cache;

import com.sun.org.glassfish.gmbal.Description;
import com.vergilyn.examples.mybatis.cache.mapper.MybatisCacheMapper;

import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 * cache-key: {@linkplain BaseExecutor#createCacheKey(MappedStatement, Object, RowBounds, BoundSql)}
 *
 * @author vergilyn
 * @since 2021-02-20
 */
public class SqlSessionTest extends AbstractMybatisCache1stTest {

	@Test
	@Description("只会打印1次SQL。同一个SqlSession内共享local-cache")
	public void localCache() {
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		MybatisCacheMapper mapper = sqlSession.getMapper(MybatisCacheMapper.class);

		System.out.println("[1]findById(...) >>> " + mapper.findById(ID));
		System.out.println("[2]findById(...) >>> " + mapper.findById(ID));
		System.out.println("[3]findById(...) >>> " + mapper.findById(ID));

		sqlSession.close();
	}

	@Test
	@Description("打印2次SQL。同一个SqlSession多个namespace/Mapper无法共享local-cache")
	public void multiMapper() {
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		MybatisCacheMapper mapper_1 = sqlSession.getMapper(MybatisCacheMapper.class);
		System.out.println("[1]findById(...) >>> " + mapper_1.findById(ID));
		System.out.println("[2]findById(...) >>> " + mapper_1.findById(ID));

		// 因为还是相同的 namespace: `MybatisCacheMapper`，所以可以共享local-cache
		MybatisCacheMapper mapper_2 = sqlSession.getMapper(MybatisCacheMapper.class);
		System.out.println("[3]findById(...) >>> " + mapper_2.findById(ID));
		System.out.println("[4]findById(...) >>> " + mapper_2.findById(ID));

		// 即使是同一个SqlSession，同一张table，但是 namespace/mapper不同，也无法共享local-cache
		MybatisCacheMapperCopy mapper_3 = sqlSession.getMapper(MybatisCacheMapperCopy.class);
		System.out.println("[5]findById(...) >>> " + mapper_3.findById(ID));
		System.out.println("[6]findById(...) >>> " + mapper_3.findById(ID));

		sqlSession.close();
	}

	@Test
	@Description("打印2次SQL。不同SqlSession无法共享local-cache")
	public void multiSqlSession() {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(true);
		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);

		System.out.println("[1]findById(...) >>> " + mapper_1.findById(ID));
		System.out.println("[2]findById(...) >>> " + mapper_1.findById(ID));
		System.out.println("[3]findById(...) >>> " + mapper_1.findById(ID));

		SqlSession sqlSession_2 = sqlSessionFactory.openSession(true);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);

		System.out.println("[4]findById(...) >>> " + mapper_2.findById(ID));
		System.out.println("[5]findById(...) >>> " + mapper_2.findById(ID));
		System.out.println("[6]findById(...) >>> " + mapper_2.findById(ID));

		sqlSession_1.close();
		sqlSession_2.close();
	}
}
