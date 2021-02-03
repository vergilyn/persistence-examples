package com.vergilyn.examples.mybatis.plus.mapper;

import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import static com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity.TABLE_NAME;

@Mapper
public interface MybatisAnnoMapper {

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	MybatisPlusEntity annoSelectById(Long id);

	@Delete("DELETE FROM " + TABLE_NAME)
	int deleteAll();
}
