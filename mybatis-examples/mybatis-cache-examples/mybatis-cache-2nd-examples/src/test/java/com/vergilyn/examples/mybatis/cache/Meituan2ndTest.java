package com.vergilyn.examples.mybatis.cache;

import java.sql.SQLException;
import java.time.LocalTime;

import com.vergilyn.examples.mybatis.cache.mapper.MybatisCacheMapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

/**
 *
 * @author vergilyn
 * @since 2021-02-23
 *
 * @see <a href="https://tech.meituan.com/2018/01/19/mybatis-cache.html">聊聊MyBatis缓存机制</a>
 */
public class Meituan2ndTest extends AbstractMybatisCache2ndTest{

	/**
	 * 测试：不提交事务时，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。 <br/>
	 * 结论：当sqlsession没有调用`commit()`方法时，二级缓存并<b>没有</b>起作用。
	 */
	@Test
	public void testCacheWithoutCommitOrClose() throws SQLException {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(false);
		// sqlSession_1.getConnection().setAutoCommit(true);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(false);
		// sqlSession_1.getConnection().setAutoCommit(true);

		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);

		System.out.println("mapper_1 读取数据: " + mapper_1.findWithCache(ID));
		System.out.println("mapper_2 读取数据: " + mapper_2.findWithCache(ID));
	}

	/**
	 * 测试：当提交事务时，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。 <br/>
	 * 结论：sqlsession2的查询，<b>使用了缓存</b>，缓存的命中率是0.5。
	 */
	@Test
	public void testCacheWithCommitOrClose() {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(false);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(false);

		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);

		System.out.println("mapper_1 读取数据: " + mapper_1.findWithCache(ID));
		sqlSession_1.commit();
		System.out.println("mapper_2 读取数据: " + mapper_2.findWithCache(ID));
	}

	/**
	 * 测试：测试update操作是否会刷新该namespace下的二级缓存。<br/>
	 * 结论：在sqlSession3更新数据库并提交事务后，缓存被刷新（即clearCache），之后的查询先走数据库。<br/>
	 */
	@Test
	public void testCacheWithUpdate() {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(false);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(false);
		SqlSession sqlSession_3 = sqlSessionFactory.openSession(false);

		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_3 = sqlSession_3.getMapper(MybatisCacheMapper.class);

		System.out.println("mapper_1 读取数据: " + mapper_1.findWithCache(ID));
		sqlSession_1.commit();
		System.out.println("mapper_2 读取数据: " + mapper_2.findWithCache(ID));

		mapper_3.update(ID, "mapper-" + LocalTime.now());
		sqlSession_3.commit();
		System.out.println("mapper_3 update&commit...");

		System.out.println("mapper_2 读取数据: " + mapper_2.findWithCache(ID));
		System.out.println("mapper_3 读取数据: " + mapper_3.findWithCache(ID));

	}
}
