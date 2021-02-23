package com.vergilyn.examples.mybatis.transaction;

import java.time.LocalDateTime;

import com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity;
import com.vergilyn.examples.mybatis.transaction.mapper.MybatisTransactionMapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MybatisTransactionSpringApplication.class, properties = "spring.profiles.active=datasource,mybatis")
@Slf4j
public abstract class AbstractMybatisTransactionSpringTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;

	@BeforeEach
	public void beforeEach(){
		try {
			SqlSession sqlSession = sqlSessionFactory.openSession(true);

			MybatisTransactionEntity entity = buildEntity(ID);

			MybatisTransactionMapper mapper = sqlSession.getMapper(MybatisTransactionMapper.class);
			mapper.insert(entity);
			sqlSession.commit();
			sqlSession.close();

			System.out.println("beforeEach() >>>> " + entity);
		}catch (Exception e){
			// ignore
			log.warn(e.getMessage());
		}
	}

	protected MybatisTransactionEntity buildEntity(Integer id){
		MybatisTransactionEntity init = new MybatisTransactionEntity();
		init.setId(id);
		init.setCreateTime(LocalDateTime.now());
		init.setIsDeleted(false);
		init.setName("mybatis-transaction-init");
		init.setEnumField("NONE");

		return init;
	}
}
