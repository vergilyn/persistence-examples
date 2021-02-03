package com.vergilyn.examples.mybatis.plus;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;

import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;
import com.vergilyn.examples.mybatis.plus.mapper.MybatisPlusMapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author vergilyn
 * @since 2021-02-03
 */
@SpringBootTest(classes = MybatisPlusApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractMybatisPlusTest {

	@Resource
	protected MybatisPlusMapper mybatisPlusMapper;
	@Resource
	protected SqlSessionFactory sqlSessionFactory;


	protected void deleteAll(){
		int row = mybatisPlusMapper.deleteAll();
		System.out.println("deleteAll() >>>> row: " + row);
	}

	protected void truncateTable(){

		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try {

			Connection connection = sqlSession.getConnection();

			CallableStatement statement = connection
					.prepareCall("TRUNCATE TABLE " + MybatisPlusEntity.TABLE_NAME);

			statement.execute();

		}catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

}
