package com.vergilyn.examples.mybatis.annotation.mapper;

import com.vergilyn.examples.mybatis.annotation.entity.MybatisAnnotationEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@Mapper
public interface MybatisAnnotationMapper {

	@Select("SELECT * FROM mybatis_annotation WHERE id = #{id}")
	MybatisAnnotationEntity findById(Integer id);

	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	@Insert("INSERT INTO mybatis_annotation (create_time, is_deleted, name, enum_field) "
			+ " VALUES(#{createTime}, #{isDeleted}, #{name}, #{enumField})")
	int insert(MybatisAnnotationEntity entity);

	@Delete("DELETE FROM mybatis_annotation WHERE id = #{id}")
	int delete(Integer id);
}
