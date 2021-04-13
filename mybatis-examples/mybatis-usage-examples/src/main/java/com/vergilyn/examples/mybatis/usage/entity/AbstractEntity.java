package com.vergilyn.examples.mybatis.usage.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractEntity<ID> implements Serializable {
	private ID id;
}
