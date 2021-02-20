package com.vergilyn.examples.mybatis.plus.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Getter;
import lombok.Setter;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

@Setter
@Getter
public abstract class AbstractEntity<ID> implements Serializable {

	/**
	 * mybatis-plus 需要指定，否则自增长的ID不正确。
	 */
	@TableId(type = IdType.AUTO)
	private ID id;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 乐观锁
	 * <p>update的时候不能是NULL</p>
	 * @see com.vergilyn.examples.mybatis.plus.configuration.MybatisPlusInterceptorAutoConfiguration
	 */
	@com.baomidou.mybatisplus.annotation.Version
	@TableField(fill = FieldFill.UPDATE)
	private Integer version;

	private Boolean isDeleted;

	@Override
	public String toString() {
		return JSON.toJSONString(this, PrettyFormat, WriteNullStringAsEmpty);
	}
}
