package com.vergilyn.examples.mybatis.transaction;

import com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService;

import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 集成spring（使用mybatis-spring）时: <br/>
 *   每次查询spring会重新创建SqlSession，所以一级缓存是不生效的。<br/>
 *   而当开启事务时，spring会使用同一个SqlSession做查询，所以这个情况下一级缓存是生效的。<br/>
 *
 * <p>
 * 可以通过debug {@linkplain DefaultSqlSession#selectOne(String, Object)} 观察是否是同一个SqlSession对象。
 *
 * @author vergilyn
 * @since 2021-02-22
 */
public class SpringMybatisTransactionTest extends AbstractMybatisTransactionSpringTest{
	@Autowired
	private MybatisTransactionService service;

	/**
	 * 每次查询spring会重新创建SqlSession，所以一级缓存是不生效的。
	 */
	@Test
	public void noTransaction(){
		service.noTransactionQuery(ID);
	}

	/**
	 * 当开启事务时，spring会使用同一个SqlSession做查询，所以这个情况下一级缓存是生效的。
	 */
	@Test
	public void withTransaction(){
		service.withTransactionQuery(ID);
	}
}
