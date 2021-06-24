package com.vergilyn.examples.jpa.cascade.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vergilyn
 * @since 2021-06-22
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractIdEntity<ID extends Serializable> implements Serializable {
	protected static final String FIELD_ID = "id";

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = FIELD_ID)
	private ID id;
}
