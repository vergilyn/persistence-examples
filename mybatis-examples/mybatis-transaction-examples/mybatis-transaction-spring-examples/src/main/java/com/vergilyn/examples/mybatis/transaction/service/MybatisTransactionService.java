package com.vergilyn.examples.mybatis.transaction.service;

import java.util.concurrent.atomic.AtomicInteger;

import com.vergilyn.examples.mybatis.transaction.entity.MybatisTransactionEntity;
import com.vergilyn.examples.mybatis.transaction.mapper.MybatisTransactionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MybatisTransactionService {

	@Autowired
	private MybatisTransactionMapper mapper;

	public void noTransactionQuery(Integer id){
		AtomicInteger index = new AtomicInteger(0);

		printFindById(mapper.findById(id), index);
		printFindById(mapper.findById(id), index);
	}

	@Transactional
	public void withTransactionQuery(Integer id){
		AtomicInteger index = new AtomicInteger(0);

		printFindById(mapper.findById(id), index);
		printFindById(mapper.findById(id), index);
	}

	private void printFindById(MybatisTransactionEntity entity, AtomicInteger index){
		System.out.printf("[%d]findById(...) >>> %s \n", index.incrementAndGet(), entity);
	}
}
