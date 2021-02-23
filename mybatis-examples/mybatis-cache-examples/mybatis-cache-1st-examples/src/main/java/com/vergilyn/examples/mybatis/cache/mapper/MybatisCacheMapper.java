package com.vergilyn.examples.mybatis.cache.mapper;

import com.vergilyn.examples.mybatis.cache.entity.MybatisCacheEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import static com.vergilyn.examples.mybatis.cache.entity.MybatisCacheEntity.TABLE_NAME;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@Mapper
public interface MybatisCacheMapper {

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	MybatisCacheEntity findById(Integer id);

	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	@Insert("INSERT INTO " + TABLE_NAME + " (create_time, is_deleted, name, enum_field) "
			+ " VALUES(#{createTime}, #{isDeleted}, #{name}, #{enumField})")
	int insert(MybatisCacheEntity entity);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id = #{id}")
	int delete(Integer id);
}
