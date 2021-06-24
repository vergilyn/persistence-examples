package com.vergilyn.examples.jpa.cascade.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "jpa_cascade_duplex_user_order")
@Setter
@Getter
@NoArgsConstructor
public class UserOrderDuplexEntity extends AbstractIdEntity<Long> {

	@ManyToOne(optional = false)
	@JoinColumn(name = "USER_ID")
	private UserDuplexEntity user;

	private String title;

	private Integer amount;

	public UserOrderDuplexEntity(String title, Integer amount) {
		this.title = title;
		this.amount = amount;
	}
}
