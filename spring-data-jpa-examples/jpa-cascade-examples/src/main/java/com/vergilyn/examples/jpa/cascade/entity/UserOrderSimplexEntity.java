package com.vergilyn.examples.jpa.cascade.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 子表不需要common-field，所以继承{@linkplain AbstractIdEntity}
 *
 * @author vergilyn
 * @since 2021-06-22
 */
@Entity
@Table(name = "jpa_cascade_simplex_user_order")
@Setter
@Getter
@NoArgsConstructor
public class UserOrderSimplexEntity extends AbstractIdEntity<Long> {

	private Long userId;

	private String title;

	private Integer amount;

	public UserOrderSimplexEntity(String title, Integer amount) {
		this.title = title;
		this.amount = amount;
	}
}
