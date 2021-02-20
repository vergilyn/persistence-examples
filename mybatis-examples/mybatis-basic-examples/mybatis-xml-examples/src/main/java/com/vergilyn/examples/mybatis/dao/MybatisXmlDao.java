package com.vergilyn.examples.mybatis.dao;

import com.vergilyn.examples.mybatis.entity.MybatisXmlEntity;

import org.springframework.stereotype.Component;

@Component
public interface MybatisXmlDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MybatisXmlEntity record);

    int insertSelective(MybatisXmlEntity record);

    MybatisXmlEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MybatisXmlEntity record);

    int updateByPrimaryKey(MybatisXmlEntity record);
}