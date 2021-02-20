package com.vergilyn.examples.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vergilyn.examples.mybatis.plus.entity.MybatisPlusEntity;

/**
 * 场景：使用mybatis-plus提供{@linkplain BaseMapper}进行CRUD，然后手写的SQL（主要是复杂的大SQL）还是通过xml管理。
 *
 * @author vergilyn
 * @since 2021-02-03
 */
public interface MybatisPlusMapper extends BaseMapper<MybatisPlusEntity>, MybatisXmlMapper, MybatisAnnoMapper {

}
