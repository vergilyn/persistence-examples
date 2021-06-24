package com.vergilyn.examples.jpa.basic.entity;

import java.io.Serializable;

public abstract class AbstractIdEntity<ID extends Serializable> implements Serializable {
	public static final String FIELD_ID = "id";

	protected ID id;

	public void setId(ID id){
		this.id = id;
	}

	public abstract ID getId();
}
