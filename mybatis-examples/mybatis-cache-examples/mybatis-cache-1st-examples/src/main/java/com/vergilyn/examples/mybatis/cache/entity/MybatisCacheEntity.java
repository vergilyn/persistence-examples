package com.vergilyn.examples.mybatis.cache.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vergilyn
 * @since 2021-02-02
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MybatisCacheEntity extends AbstractEntity<Integer> {
	public static final String TABLE_NAME = "mybatis_cache";

	private String name;

	private String enumField;
}