package com.vergilyn.examples.mybatis.plus;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;
import com.vergilyn.examples.mybatis.plus.service.MybatisPlusService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;
import static com.vergilyn.examples.mybatis.plus.enums.MybatisEnum.FIRST;
import static com.vergilyn.examples.mybatis.plus.enums.MybatisEnum.SECOND;

/**
 *
 * <a href="https://mp.baomidou.com/guide/faq.html#%E9%80%9A%E7%94%A8-insertbatch-%E4%B8%BA%E4%BB%80%E4%B9%88%E6%94%BE%E5%9C%A8-service-%E5%B1%82%E5%A4%84%E7%90%86">
 *     通用 insertBatch 为什么放在 service 层处理</a>
 * <pre>
 * 1. SQL 长度有限制海量数据量单条 SQL 无法执行，就算可执行也容易引起内存泄露 JDBC 连接超时等
 * 2. 不同数据库对于单条 SQL 批量语法不一样不利于通用
 * 3. <b>目前的解决方案：循环预处理批量提交，虽然性能比单 SQL 慢但是可以解决以上问题。</b>
 * </pre>
 *
 * @author vergilyn
 * @since 2021-02-03
 *
 * @see com.baomidou.mybatisplus.extension.service.IService
 */
public class BatchInsertOrUpdateTest extends AbstractMybatisPlusTest {

	@Autowired
	private MybatisPlusService mybatisPlusService;

	private static String _name_prefix = "batch-";
	private static List<Long> _ids;
	private static Integer _default_version = 1;

	@Test
	@Order(1)
	public void batchInsert(){

		int size = 10;
		AtomicInteger index = new AtomicInteger(0);
		List<MybatisPlusEntity> list = Stream
				.generate(() -> {
					MybatisPlusEntity entity = new MybatisPlusEntity();
					entity.setName(_name_prefix + index.incrementAndGet());
					entity.setEnumField(FIRST);
					entity.setVersion(_default_version);
					return entity;
				})
				.limit(size)
				.collect(Collectors.toList());

		mybatisPlusService.saveBatch(list);

		_ids = list.stream().map(MybatisPlusEntity::getId).collect(Collectors.toList());

		System.out.println("init() >>>> ids: " + _ids);
	}

	@Test
	@Order(2)
	public void batchUpdate(){

		List<MybatisPlusEntity> entities = _ids.stream()
				.map(id -> {
					MybatisPlusEntity entity = new MybatisPlusEntity();
					entity.setId(id);
					entity.setEnumField(SECOND);
					entity.setVersion(_default_version);

					return entity;
				}).collect(Collectors.toList());

		mybatisPlusService.updateBatchById(entities);
	}

	@Test
	@Order(3)
	public void valid(){
		QueryWrapper<MybatisPlusEntity> queryWrapper = new QueryWrapper<MybatisPlusEntity>();
		queryWrapper.in("id", _ids);

		List<MybatisPlusEntity> entities = mybatisPlusMapper.selectList(queryWrapper);

		entities.forEach(entity -> {
			Assertions.assertEquals(_default_version + 1, entity.getVersion());
			Assertions.assertEquals(SECOND, entity.getEnumField());
		});

		System.out.println("valid() >>>> " + JSON.toJSONString(entities, PrettyFormat, WriteNullStringAsEmpty));

	}

	@Test
	@Order(4)
	public void clearData(){
		deleteAll();
	}
}
