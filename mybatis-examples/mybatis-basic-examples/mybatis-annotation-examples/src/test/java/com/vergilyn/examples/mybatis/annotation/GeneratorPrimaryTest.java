package com.vergilyn.examples.mybatis.annotation;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import com.vergilyn.examples.mybatis.annotation.entity.MybatisAnnotationEntity;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;

import static com.vergilyn.examples.mybatis.annotation.entity.MybatisAnnotationEntity.TABLE_NAME;

/**
 * 前提，mysql中设置`id AUTO_INCREMENT`。
 *
 * <p>
 * 1. `entity.id = null`，则自动生成ID。<br/>
 * 2. `entity.id = 1`，则生成`ID=1`的数据。<br/>
 *
 * @author vergilyn
 * @since 2021-02-23
 */
public class GeneratorPrimaryTest extends AbstractMybatisAnnotationTest {

	@Autowired
	private LoggingSystem loggingSystem;

	protected static interface GeneratorPrimaryMapper{
		@Options(keyProperty = "id", useGeneratedKeys = true)
		@Insert("INSERT INTO " + TABLE_NAME + " (id, create_time, is_deleted, name, enum_field) "
				+ " VALUES(#{id}, #{createTime}, #{isDeleted}, #{name}, #{enumField})")
		int insert(MybatisAnnotationEntity entity);

		/* 1) `entity.id = 1`，当insert后`entity.getId()` Expected: 1, Actual: `LAST_INSERT_ID()`.
		 * 2) `entity.id = null`，当insert后`entity.getId()`结果正常。
		 */
		@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
		@Insert("INSERT INTO " + TABLE_NAME + " (id, create_time, is_deleted, name, enum_field) "
				+ " VALUES(#{id}, #{createTime}, #{isDeleted}, #{name}, #{enumField})")
		int insertBad(MybatisAnnotationEntity entity);
	}

	@BeforeEach
	public void beforeEach(){
		boolean hasMapper = sqlSessionFactory.getConfiguration().hasMapper(GeneratorPrimaryMapper.class);
		if (!hasMapper){
			sqlSessionFactory.getConfiguration().addMapper(GeneratorPrimaryMapper.class);
			loggingSystem.setLogLevel(GeneratorPrimaryMapper.class.getName(), LogLevel.TRACE);
		}

		System.out.println("beforeEach() >>>> deleteAll rows: " + super.mapper.deleteAll());
	}

	static Stream<Integer> testParamProvider() {
		return Stream.of(ID, null);
	}

	@ParameterizedTest
	@MethodSource("testParamProvider")
	public void insert(Integer id){
		MybatisAnnotationEntity entity = build(id);

		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		GeneratorPrimaryMapper mapper = sqlSession.getMapper(GeneratorPrimaryMapper.class);

		System.out.println("expected entity >>>> " + entity);
		mapper.insert(entity);
		System.out.println("insert-after entity >>>> " + entity);

		if (id == null){
			Assertions.assertNotNull(entity.getId());
		}else {
			Assertions.assertEquals(id, entity.getId());
		}

		System.out.printf("insert-after get(%d) >>>> %s \n", entity.getId(), super.mapper.findById(entity.getId()));

		sqlSession.close();
	}

	@ParameterizedTest
	@MethodSource("testParamProvider")
	public void insertBad(Integer id){
		MybatisAnnotationEntity entity = build(id);

		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		GeneratorPrimaryMapper mapper = sqlSession.getMapper(GeneratorPrimaryMapper.class);

		System.out.println("expected entity >>>> " + entity);
		mapper.insertBad(entity);
		System.out.println("insert-after entity >>>> " + entity);

		if (id == null){
			Assertions.assertNotNull(entity.getId());
		}else {
			Assertions.assertEquals(id, entity.getId());
		}

		System.out.printf("insert-after get(%d) >>>> %s \n", entity.getId(), super.mapper.findById(entity.getId()));

		sqlSession.close();
	}

	@AfterEach
	public void AfterEach(){
		System.out.println("AfterEach() >>>> deleteAll rows: " + super.mapper.deleteAll());
	}

	private MybatisAnnotationEntity build(Integer id){
		MybatisAnnotationEntity entity = new MybatisAnnotationEntity();
		entity.setId(id);
		entity.setCreateTime(LocalDateTime.now());
		entity.setIsDeleted(false);
		entity.setName("specify-pk-" + entity.getId());
		entity.setEnumField("NONE");

		return entity;
	}
}
