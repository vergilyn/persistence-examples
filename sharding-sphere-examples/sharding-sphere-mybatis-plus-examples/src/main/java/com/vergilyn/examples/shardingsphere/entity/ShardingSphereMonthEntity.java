package com.vergilyn.examples.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName(ShardingSphereMonthEntity.TABLE_NAME)
public class ShardingSphereMonthEntity {
	public static final String TABLE_NAME = "sharding_sphere_month";

	@TableId//(type = IdType.AUTO)
	private String id;

	private LocalDateTime createTime;

	private String name;
}
