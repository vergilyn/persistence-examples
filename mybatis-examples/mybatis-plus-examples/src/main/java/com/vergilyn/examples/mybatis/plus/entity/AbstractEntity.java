package com.vergilyn.examples.mybatis.plus.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractEntity<ID> implements Serializable {

	/**
	 * mybatis-plus 需要指定，否则自增长的ID不正确。
	 */
	@TableId(type = IdType.AUTO)
	private ID id;

	private LocalDateTime createTime;
	private Boolean isDeleted;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
