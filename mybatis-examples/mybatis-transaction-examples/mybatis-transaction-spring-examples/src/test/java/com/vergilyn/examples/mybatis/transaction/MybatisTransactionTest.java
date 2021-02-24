package com.vergilyn.examples.mybatis.transaction;

import com.alibaba.druid.pool.DruidDataSource;
import com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity;
import com.vergilyn.examples.mybatis.transaction.mapper.MybatisTransactionMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

/**
 *
 * @author vergilyn
 * @since 2021-02-20
 */
@SpringBootTest(classes = MybatisTransactionSpringApplication.class,
		properties = {"spring.profiles.active=datasource,mybatis",
				"spring.datasource.druid.default-auto-commit=true"})
@TestConfiguration
public class MybatisTransactionTest extends AbstractMybatisTransactionSpringTest{

	/**
	 * @see SpringManagedTransaction#openConnection()
	 * @see DruidDataSource#defaultAutoCommit
	 */
	@Test
	public void autocommit(){
		/**
		 * autocommit >>>> 实际还是由 {@linkplain DruidDataSource#defaultAutoCommit} 控制！
		 *
		 * see: {@linkplain SpringManagedTransaction#openConnection()}
		 */
		SqlSession sqlSession = sqlSessionFactory.openSession(false);

		// default >>>> `spring.datasource.druid.default-auto-commit = true`
		// sqlSession.getConnection().setAutoCommit(true);

		MybatisTransactionMapper mapper = sqlSession.getMapper(MybatisTransactionMapper.class);

		MybatisTransactionEntity before = mapper.findById(ID);
		System.out.println("delete before >>> " + before);

		/**
		 * delete >>>> {@linkplain DefaultSqlSession#delete(String, Object)}
		 */
		System.out.println("delete(...) >>> rows: " + mapper.delete(ID));
		// sqlSession.commit();  // 由于`connection.autoCommit = true`，所以还是会自动提交事务。

		MybatisTransactionEntity after = mapper.findById(ID);
		System.out.println("delete after >>> " + after);

		// 恒等于NULL，即使`autocommit=false`，因为处于同一个事务。
		Assertions.assertNull(after);
	}
}
