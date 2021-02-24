package com.vergilyn.examples.mybatis.transaction;

import java.time.LocalDateTime;

import com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity;
import com.vergilyn.examples.mybatis.transaction.mapper.MybatisTransactionMapper;
import com.vergilyn.examples.mybatis.transaction.service.MybatisTransactionService;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = MybatisTransactionSpringApplication.class, properties = "spring.profiles.active=datasource,mybatis")
public abstract class AbstractMybatisTransactionSpringTest {
	protected static final Integer ID = 1;

	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	protected MybatisTransactionService service;
	@Autowired
	protected MybatisTransactionMapper mapper;

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
			log.warn("beforeEach() >>>> {}", e.getMessage());
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
