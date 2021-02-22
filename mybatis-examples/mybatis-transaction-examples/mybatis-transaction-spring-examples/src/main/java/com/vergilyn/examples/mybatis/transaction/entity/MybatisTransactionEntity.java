package com.vergilyn.examples.mybatis.transaction.entity;

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
public class MybatisTransactionEntity extends AbstractEntity<Integer> {
	public static final String TABLE_NAME = "mybatis_transaction";

	private String name;

	private String enumField;
}