package com.vergilyn.examples.shardingsphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vergilyn.examples.shardingsphere.entity.ShardingSphereMonthEntity;
import com.vergilyn.examples.shardingsphere.mapper.ShardingSphereMonthMapper;
import com.vergilyn.examples.shardingsphere.service.ShardingSphereMonthService;
import org.springframework.stereotype.Service;

@Service
public class ShardingSphereMonthServiceImpl extends ServiceImpl<ShardingSphereMonthMapper, ShardingSphereMonthEntity>
		implements ShardingSphereMonthService {
}
