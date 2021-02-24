package com.vergilyn.examples.mybatis.cache;

import java.sql.SQLException;
import java.time.LocalTime;

import com.vergilyn.examples.mybatis.cache.mapper.MybatisCacheMapper;

import org.apache.ibatis.cache.TransactionalCacheManager;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author vergilyn
 * @since 2021-02-23
 *
 * @see <a href="https://tech.meituan.com/2018/01/19/mybatis-cache.html">聊聊MyBatis缓存机制</a>
 */
@SpringBootTest(classes = MybatisCache2ndApplication.class,
		properties = {"spring.profiles.active=datasource,mybatis",
				"spring.datasource.druid.default-auto-commit=true"})
public class Meituan2ndTest extends AbstractMybatisCache2ndTest {

	/**
	 * 测试：不提交事务时，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。 <br/>
	 * 结论：当sqlsession没有调用`commit()`方法时，二级缓存并<b>没有</b>起作用。
	 */
	@Test
	public void testCacheWithoutCommitOrClose() throws SQLException {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(true);
		// sqlSession_1.getConnection().setAutoCommit(true);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(true);
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
	public void testCacheWithCommitOrClose() throws SQLException {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(true);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(true);

		MybatisCacheMapper mapper_1 = sqlSession_1.getMapper(MybatisCacheMapper.class);
		MybatisCacheMapper mapper_2 = sqlSession_2.getMapper(MybatisCacheMapper.class);

		System.out.println("mapper_1 读取数据: " + mapper_1.findWithCache(ID));

		/**
		 * autocommit 不会触发以下调用链，所以必须显示调用才可以让耳机缓存起作用！
		 *
		 * {@linkplain DefaultSqlSession#commit(boolean)}
		 *   -> {@linkplain CachingExecutor#commit(boolean)}
		 *     -> {@linkplain BaseExecutor#commit(boolean)}
		 *     -> {@linkplain TransactionalCacheManager#commit()}: 核心
		 */
		sqlSession_1.commit();

		System.out.println("mapper_2 读取数据: " + mapper_2.findWithCache(ID));
	}

	/**
	 * 测试：测试update操作是否会刷新该namespace下的二级缓存。<br/>
	 * 结论：在sqlSession3更新数据库<b>并提交事务</b>后，缓存被刷新（即clearCache），之后的查询先走数据库。<br/>
	 */
	@Test
	public void testCacheWithUpdate() {
		SqlSession sqlSession_1 = sqlSessionFactory.openSession(true);
		SqlSession sqlSession_2 = sqlSessionFactory.openSession(true);
		SqlSession sqlSession_3 = sqlSessionFactory.openSession(true);

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
