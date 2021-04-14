package com.vergilyn.examples.mybatis.usage.entity;

import com.alibaba.fastjson.JSON;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteMapNullValue;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullBooleanAsFalse;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullListAsEmpty;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullNumberAsZero;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

public abstract class AbstractLongEntity extends AbstractEntity<Long> {

	@Override
	public String toString() {
		return JSON.toJSONString(this,
				WriteMapNullValue,
				WriteNullListAsEmpty,
				WriteNullStringAsEmpty,
				WriteNullNumberAsZero,
				WriteNullBooleanAsFalse);
	}
}
