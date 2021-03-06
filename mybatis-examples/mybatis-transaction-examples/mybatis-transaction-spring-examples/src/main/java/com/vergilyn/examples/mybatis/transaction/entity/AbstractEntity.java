package com.vergilyn.examples.mybatis.transaction.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractEntity<ID> implements Serializable {

	private ID id;
	private LocalDateTime createTime;
	private Boolean isDeleted;

	@Override
	public String toString() {
		return Integer.toHexString(hashCode()) + "@" + JSON.toJSONString(this);
	}
}
