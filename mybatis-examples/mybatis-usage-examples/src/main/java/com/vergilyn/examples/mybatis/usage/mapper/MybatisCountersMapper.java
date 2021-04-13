package com.vergilyn.examples.mybatis.usage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vergilyn.examples.mybatis.usage.entity.MybatisCountersEntity;

public interface MybatisCountersMapper extends BaseMapper<MybatisCountersEntity> {

	void incr(Long id);

	/**
	 *
	 * @see <a href="https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#Result_Maps">关联的嵌套 Select 查询</a>
	 */
	MybatisCountersEntity incrByMybatisCascade(Long id);
}
