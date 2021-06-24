package com.vergilyn.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DeclareJdbcTxTests {
	DruidDataSource datasource = datasource();
	JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
	NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(datasource);

	@Test
	public void single(){
		update(1, 2);
	}

	@Test
	public void txPropagation(){
		update(10, 11);
		update(12, 13);
	}

	@SneakyThrows
	@Test
	public void concurrent(){
		final ExecutorService threadPool = Executors.newFixedThreadPool(2);

		threadPool.submit(() -> {
			update(1, 2);
		});
		threadPool.submit(() -> {
			update(2, 3);
		});

		TimeUnit.SECONDS.sleep(10);
	}

	@SneakyThrows
	public void update(Integer id1, Integer id2){

		TimeUnit.SECONDS.sleep(1);
		final TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

		try {
			jdbcTemplate.update("INSERT INTO `user`(`ID`, `NAME`) VALUES (?, ?)", id1, "NAME");
			jdbcTemplate.update("INSERT INTO `user`(`ID`, `NAME`) VALUES (?, ?)", id2, "NAME");
			transactionManager.commit(transactionStatus);

		}catch (Exception e){
			System.out.println("[vergilyn] >>>> rollback: " + id1 + "," + id2);
			transactionManager.rollback(transactionStatus);
		}
	}



	public DruidDataSource datasource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/dev_vergilyn?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Chongqing");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setDefaultAutoCommit(true);
		return dataSource;
	}
}
