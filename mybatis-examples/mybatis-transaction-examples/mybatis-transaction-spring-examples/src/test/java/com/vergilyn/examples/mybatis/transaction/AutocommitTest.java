package com.vergilyn.examples.mybatis.transaction;

import java.lang.reflect.Method;
import java.sql.SQLException;

import com.vergilyn.examples.mybatis.transaction.mapper.MybatisTransactionMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationUtils;

/**
 * 直接通过{@linkplain #sqlSession()}方式获取的mapper未被mybatis-spring代理，
 * 所以必须显示调用{@linkplain SqlSession#commit()}，
 * 涉及到 1st-cache & 2nd-cache 的 clear/shared。
 *
 * @author vergilyn
 * @since 2021-02-23
 */
@SuppressWarnings("JavadocReference")
@SpringBootTest(classes = MybatisTransactionSpringApplication.class,
		properties = {"spring.profiles.active=datasource,mybatis",
				"spring.datasource.druid.default-auto-commit=true"})
public class AutocommitTest extends AbstractMybatisTransactionSpringTest {

	/**
	 * service层 未指定`{@linkplain Transactional @Transactional}`时，
	 * mybatis 通过 {@linkplain SqlSessionTemplate.SqlSessionInterceptor#invoke(Object, Method, Object[])}
	 * 代理调用{@linkplain DefaultSqlSession#commit(boolean)}。
	 *
	 * <p/>
	 * <pre>
	 *     at org.apache.ibatis.session.defaults.DefaultSqlSession.commit(DefaultSqlSession.java:223)
	 * 	   at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:431)
	 * 	   at com.sun.proxy.$Proxy55.selectOne(Unknown Source:-1)
	 * 	   at org.mybatis.spring.SqlSessionTemplate.selectOne(SqlSessionTemplate.java:160)
	 * 	   at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:87)
	 * 	   at org.apache.ibatis.binding.MapperProxy$PlainMethodInvoker.invoke(MapperProxy.java:152)
	 * 	   at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:85)
	 * 	   at com.sun.proxy.$Proxy61.findById(Unknown Source:-1)
	 * 	   at com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService.noTransactionQuery(MybatisTransactionService.java:21)
	 * 	   at com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService$$FastClassBySpringCGLIB$$d281c966.invoke(<generated>:-1)
	 * 	   at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
	 * 	   at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:685)
	 * 	   at com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService$$EnhancerBySpringCGLIB$$94febea6.noTransactionQuery(<generated>:-1)
	 * 	   at com.vergilyn.examples.mybatis.transaction.AutocommitTest.serviceNoTransactionQuery(AutocommitTest.java:25)
	 * </pre>
	 */
	@Test
	public void serviceNoTransactionQuery(){
		// 调用2次 DefaultSqlSession#commit(true)
		service.noTransactionQuery(ID);
	}

	/**
	 * 因为将事务交由spring，所以mybatis在事务提交前{@linkplain TransactionSynchronizationUtils#triggerBeforeCommit(boolean)}
	 * 触发并调用{@linkplain DefaultSqlSession#commit()}
	 *
	 * <pre>
	 *     at org.apache.ibatis.session.defaults.DefaultSqlSession.commit(DefaultSqlSession.java:223)
	 *     at org.apache.ibatis.session.defaults.DefaultSqlSession.commit(DefaultSqlSession.java:217)
	 *     at org.mybatis.spring.SqlSessionUtils$SqlSessionSynchronization.beforeCommit(SqlSessionUtils.java:283)
	 *     at org.springframework.transaction.support.TransactionSynchronizationUtils.triggerBeforeCommit(TransactionSynchronizationUtils.java:96)
	 *     at org.springframework.transaction.support.AbstractPlatformTransactionManager.triggerBeforeCommit(AbstractPlatformTransactionManager.java:920)
	 *     at org.springframework.transaction.support.AbstractPlatformTransactionManager.processCommit(AbstractPlatformTransactionManager.java:728)
	 *     at org.springframework.transaction.support.AbstractPlatformTransactionManager.commit(AbstractPlatformTransactionManager.java:712)
	 *     at org.springframework.transaction.interceptor.TransactionAspectSupport.commitTransactionAfterReturning(TransactionAspectSupport.java:631)
	 *     at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:385)
	 *     at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)
	 *     at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
	 *     at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)
	 *     at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)
	 *     at com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService$$EnhancerBySpringCGLIB$$87120527.withTransactionQuery(<generated>:-1)
	 *     at com.vergilyn.examples.mybatis.transaction.AutocommitTest.serviceWithTransactionQuery(AutocommitTest.java:33)
	 * </pre>
	 */
	@Test
	public void serviceWithTransactionQuery(){
		// 调用1次 DefaultSqlSession#commit()
		service.withTransactionQuery(ID);
	}

	/**
	 * <pre>
     *    at org.apache.ibatis.session.defaults.DefaultSqlSession.commit(DefaultSqlSession.java:223)
	 * 	  at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:431)
	 * 	  at com.sun.proxy.$Proxy55.selectOne(Unknown Source:-1)
	 * 	  at org.mybatis.spring.SqlSessionTemplate.selectOne(SqlSessionTemplate.java:160)
	 * 	  at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:87)
	 * 	  at org.apache.ibatis.binding.MapperProxy$PlainMethodInvoker.invoke(MapperProxy.java:152)
	 * 	  at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:85)
	 * 	  at com.sun.proxy.$Proxy61.findById(Unknown Source:-1)
	 * 	  at com.vergilyn.examples.mybatis.transaction.AutocommitTest.mapper(AutocommitTest.java:90)
	 * </pre>
	 */
	@Test
	public void mapper(){
		mapper.findById(ID);
	}

	/**
	 * 同 {@linkplain #mapper()}
	 *
	 * <p/>
	 * <pre>
	 *    at org.apache.ibatis.session.defaults.DefaultSqlSession.commit(DefaultSqlSession.java:223)
	 * 	  at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:431)
	 * 	  at com.sun.proxy.$Proxy55.selectOne(Unknown Source:-1)
	 * 	  at org.mybatis.spring.SqlSessionTemplate.selectOne(SqlSessionTemplate.java:160)
	 * 	  at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:87)
	 * 	  at org.apache.ibatis.binding.MapperProxy$PlainMethodInvoker.invoke(MapperProxy.java:152)
	 * 	  at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:85)
	 * 	  at com.sun.proxy.$Proxy61.findById(Unknown Source:-1)
	 * 	  at com.vergilyn.examples.mybatis.transaction.AutocommitTest.sqlTemplate(AutocommitTest.java:127)
	 * </pre>
	 */
	@Test
	public void sqlTemplate(){
		MybatisTransactionMapper mapper = sqlSessionTemplate.getMapper(MybatisTransactionMapper.class);
		mapper.findById(ID);
	}

	/**
	 * 通过这种方式获取的{@code mapper}需要手动调用{@code `commit()`}！
	 *
	 * <p/>
	 * <pre>
	 *    at org.apache.ibatis.session.defaults.DefaultSqlSession.selectOne(DefaultSqlSession.java:76)
	 * 	  at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:87)
	 * 	  at org.apache.ibatis.binding.MapperProxy$PlainMethodInvoker.invoke(MapperProxy.java:152)
	 * 	  at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:85)
	 * 	  at com.sun.proxy.$Proxy61.findById(Unknown Source:-1)
	 * 	  at com.vergilyn.examples.mybatis.transaction.AutocommitTest.sqlSession(AutocommitTest.java:109)
	 * </pre>
	 *
	 * @see SqlSessionTemplate.SqlSessionInterceptor
	 * @see org.apache.ibatis.session.SqlSessionManager.SqlSessionInterceptor
	 */
	@Test
	public void sqlSession() throws SQLException {
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		sqlSession.getConnection().setAutoCommit(true);

		MybatisTransactionMapper mapper = sqlSession.getMapper(MybatisTransactionMapper.class);

		mapper.findById(ID);

		// 必须手动调用 commit
		sqlSession.commit();
	}
}
