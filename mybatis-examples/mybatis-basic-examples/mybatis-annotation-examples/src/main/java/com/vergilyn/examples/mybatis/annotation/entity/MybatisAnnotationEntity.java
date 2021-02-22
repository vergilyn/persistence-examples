package com.vergilyn.examples.mybatis.annotation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author vergilyn
 * @since 2021-02-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MybatisAnnotationEntity extends AbstractEntity<Integer> {
	public static final String TABLE_NAME = "mybatis_annotation";

	private String name;

	private String enumField;
}