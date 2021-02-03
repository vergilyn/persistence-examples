package com.vergilyn.examples.mybatis.plus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.vergilyn.examples.mybatis.plus.configuration.MybatisPlusInterceptorAutoConfiguration;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.vergilyn.examples.mybatis.plus.enums.MybatisEnum.FIRST;
import static com.vergilyn.examples.mybatis.plus.enums.MybatisEnum.NONE;
import static com.vergilyn.examples.mybatis.plus.enums.MybatisEnum.SECOND;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author vergilyn
 * @since 2021-02-03
 *
 * @see MybatisPlusInterceptorAutoConfiguration#optimisticLockerInnerInterceptor(MybatisPlusInterceptor)
 */
public class OptimisticLockTest extends AbstractMybatisPlusTest{

	private static String _name = "mybatis-optimistic-lock";
	private static Long _id;
	private static Integer _default_version = 2;

	@Order(1)
	@Test
	public void init(){
		MybatisPlusEntity entity = new MybatisPlusEntity();
		entity.setName(_name);
		entity.setEnumField(NONE);
		entity.setVersion(_default_version);

		mybatisPlusMapper.insert(entity);

		_id = entity.getId();

		entity = mybatisPlusMapper.selectById(_id);
		System.out.println("init() >>>> \n" + entity);
	}

	@Order(2)
	@RepeatedTest(2)
	@Execution(ExecutionMode.CONCURRENT)
	public void concurrent(){
		long threadId = Thread.currentThread().getId();

		// 实际开发中，如果使用乐观锁，一般都会是：get-update (2次数据库操作)
		// MybatisPlusEntity update = mybatisPlusMapper.selectById(_id);

		MybatisPlusEntity update = new MybatisPlusEntity();
		update.setId(_id);
		update.setEnumField(threadId % 2 == 0 ? FIRST : SECOND);

		// 指的是需要匹配的 version，成功更新后database中实际是 version=3
		// UPDATE mybatis_plus SET ... WHERE id=? AND version=?
		update.setVersion(_default_version);

		int row = mybatisPlusMapper.updateById(update);
		System.out.printf("concurrent() >>>> threadId: %d, row: %d \n", threadId, row);
	}

	@Order(4)
	@Test
	public void after(){
		MybatisPlusEntity entity = mybatisPlusMapper.selectById(_id);

		System.out.println("concurrent after >>>> \n" + entity);

		assertEquals(_default_version + 1, entity.getVersion());
	}

	@Order(5)
	@Test
	public void delete(){
		int row = mybatisPlusMapper.deleteById(_id);

		System.out.println("delete() >>>> " + row);

		assertEquals(1, row);
	}
}
