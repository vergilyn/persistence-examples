package com.vergilyn.examples.mybatis.usage.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@TableName(value = MybatisCountersEntity.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MybatisCountersEntity extends AbstractLongEntity{
	public static final String TABLE_NAME = "mybatis_counters";

	private Integer counter;
}
