package com.vergilyn.examples.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author vergilyn
 * @since 2021-02-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "mybatis_plus")
public class MybatisPlusEntity extends AbstractEntity<Long> {

	private String name;

	private String enumField;

	// private String newField;
}