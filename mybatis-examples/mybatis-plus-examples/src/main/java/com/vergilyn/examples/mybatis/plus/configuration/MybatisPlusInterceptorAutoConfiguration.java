package com.vergilyn.examples.mybatis.plus.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author vergilyn
 * @since 2021-02-03
 */
@Configuration
public class MybatisPlusInterceptorAutoConfiguration {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		return new MybatisPlusInterceptor();
	}

	/**
	 * 乐观锁插件
	 * <pre>
	 * 1. 支持的数据类型只有:int/Integer, long/Long, Date/Timestamp/LocalDateTime
	 * 2. 整数类型下 newVersion = oldVersion + 1
	 * 3. newVersion 会回写到 entity 中
	 * 4. 仅支持 updateById(id) 与 update(entity, wrapper) 方法
	 * 5. 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
	 * </pre>
	 *
	 * 备注：<br/>
	 * <b>1. update 时，`version` 必须NOT-NULL</b> <br/>
	 * 2. 仅支持 {@linkplain BaseMapper#updateById(Object)} 和 {@linkplain BaseMapper#update(Object, Wrapper)}，
	 *   在 {@linkplain BaseMapper#update(Object, Wrapper)} 方法下, wrapper 不能复用!!! <br/>
	 *
	 * @see <a href="https://mp.baomidou.com/guide/interceptor-optimistic-locker.html">乐观锁</a>
	 * @see <a href="https://www.cnblogs.com/54chensongxia/p/14253188.html">MyBatis-Plus 实现乐观锁更新功能</a>
	 */
	@Bean
	public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor(MybatisPlusInterceptor plusInterceptor){
		OptimisticLockerInnerInterceptor optimisticLocker = new OptimisticLockerInnerInterceptor();
		plusInterceptor.addInnerInterceptor(optimisticLocker);
		return optimisticLocker;
	}

	/**
	 * 分页插件
	 *
	 * 备注：<br/>
	 * 1. 生成 countSql 会在 left join 的表不参与 where 条件的情况下, <b>把 left join 优化掉</b>。
	 *   所以建议任何带有 left join 的sql,都写标准sql既给于表一个别名,字段也要 `别名.字段`
	 *
	 * @see <a href="https://mp.baomidou.com/guide/interceptor-pagination.html">分页</a>
	 */
	@Bean
	public PaginationInnerInterceptor paginationInnerInterceptor(MybatisPlusInterceptor plusInterceptor){
		PaginationInnerInterceptor pagination = new PaginationInnerInterceptor(DbType.MYSQL);
		plusInterceptor.addInnerInterceptor(pagination);
		return pagination;
	}
}
