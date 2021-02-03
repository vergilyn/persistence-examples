package com.vergilyn.examples.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.vergilyn.examples.mybatis.plus.enums.MybatisEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * @author vergilyn
 * @since 2021-02-02
 */
@TableName(value = MybatisPlusEntity.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MybatisPlusEntity extends AbstractEntity<Long> {
	public static final String TABLE_NAME = "mybatis_plus";

	private String name;

	/**
	 * @see MybatisEnumTypeHandler
	 */
	@TableField(typeHandler = EnumTypeHandler.class)
	private MybatisEnum enumField;

	// private String newField;
}