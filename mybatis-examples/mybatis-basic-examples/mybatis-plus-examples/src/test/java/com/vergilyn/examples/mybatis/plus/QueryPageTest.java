package com.vergilyn.examples.mybatis.plus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.vergilyn.examples.mybatis.plus.configuration.MybatisPlusInterceptorAutoConfiguration;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author vergilyn
 * @since 2021-02-03
 *
 * @see MybatisPlusInterceptorAutoConfiguration#paginationInnerInterceptor(MybatisPlusInterceptor)
 */
public class QueryPageTest extends AbstractMybatisPlusTest{

	private static List<Long> _ids = Lists.newArrayList();
	private static String _name_prefix = "mybatis-page-";

	@Test
	@Order(1)
	public void init(){
		AtomicLong index = new AtomicLong(0);

		MybatisPlusEntity entity;
		while (index.getAndIncrement() < 10){
			entity = new MybatisPlusEntity();
			entity.setName(_name_prefix + index.get());

			mybatisPlusMapper.insert(entity);

			_ids.add(entity.getId());
		}

		System.out.println("init() >>>> ids: " + JSON.toJSONString(_ids));
	}

	@Test
	@Order(2)
	public void queryPage(){
		Page<MybatisPlusEntity> page = new Page<>(0, 2);

		LambdaQueryWrapper<MybatisPlusEntity> wrapper = new QueryWrapper<MybatisPlusEntity>().lambda()
					.eq(MybatisPlusEntity::getIsDeleted,false);

		Page<MybatisPlusEntity> pageResult = mybatisPlusMapper.selectPage(page, wrapper);

		System.out.printf("queryPage() >>>> total: %d, pages: %d, size: %d, current: %d, hasNext: %b \n",
				pageResult.getTotal(), pageResult.getPages(),
				pageResult.getSize(), pageResult.getCurrent(), pageResult.hasNext());

		System.out.println("queryPage() result >>>> " + pageResult.getRecords());
	}

	@Test
	@Order(3)
	public void clear(){
		deleteAll();
	}
}
