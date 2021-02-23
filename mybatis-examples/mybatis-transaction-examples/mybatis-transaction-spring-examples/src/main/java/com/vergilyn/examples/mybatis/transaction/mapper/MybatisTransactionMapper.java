package com.vergilyn.examples.mybatis.transaction.mapper;

import com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import static com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity.TABLE_NAME;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@Mapper
public interface MybatisTransactionMapper {

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	MybatisTransactionEntity findById(Integer id);

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("INSERT INTO " + TABLE_NAME + " (id, create_time, is_deleted, name, enum_field) "
			+ " VALUES(#{id}, #{createTime}, #{isDeleted}, #{name}, #{enumField})")
	int insert(MybatisTransactionEntity entity);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id = #{id}")
	int delete(Integer id);
}
