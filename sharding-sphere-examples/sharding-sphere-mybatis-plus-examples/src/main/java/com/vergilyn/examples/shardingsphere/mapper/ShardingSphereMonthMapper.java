package com.vergilyn.examples.shardingsphere.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vergilyn.examples.shardingsphere.entity.ShardingSphereMonthEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShardingSphereMonthMapper extends BaseMapper<ShardingSphereMonthEntity> {
}
