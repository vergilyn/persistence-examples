package com.vergilyn.examples.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;
import com.vergilyn.examples.mybatis.plus.mapper.MybatisPlusMapper;
import com.vergilyn.examples.mybatis.plus.service.MybatisPlusService;

import org.springframework.stereotype.Service;

/**
 *
 * @author vergilyn
 * @since 2021-02-03
 */
@Service
public class MybatisPlusServiceImpl
		extends ServiceImpl<MybatisPlusMapper, MybatisPlusEntity>
		implements MybatisPlusService {

}
