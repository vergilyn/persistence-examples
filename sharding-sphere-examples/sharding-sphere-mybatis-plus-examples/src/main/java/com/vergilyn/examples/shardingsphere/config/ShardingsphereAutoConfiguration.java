package com.vergilyn.examples.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.spring.boot.prop.SpringBootPropertiesConfiguration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <a href="https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/spring-boot-starter/">
 *     ShardingSphere-JDBC > Spring Boot Starter </a>
 *
 * @see org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(SpringBootPropertiesConfiguration.class)
public class ShardingsphereAutoConfiguration {

	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		String s = now.toString();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

		System.out.println(LocalDateTime.parse(s, dateTimeFormatter));
	}

	/**
	 * @see com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
	 */
	@Bean(initMethod = "init", name = "druidDataSource")
	public DruidDataSource druidDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	/**
	 *
	 * @see org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration
	 * @see org.apache.shardingsphere.sharding.spring.boot.ShardingRuleSpringBootConfiguration
	 */
	@Bean
	@ConditionalOnBean(DruidDataSource.class)
	public DataSource shardingSphereDataSource(DruidDataSource druidDataSource,
			ObjectProvider<List<RuleConfiguration>> rules,
			SpringBootPropertiesConfiguration prop) throws SQLException {

		Collection<RuleConfiguration> ruleConfigs = Optional.ofNullable(rules.getIfAvailable()).orElse(Collections.emptyList());

		return ShardingSphereDataSourceFactory.createDataSource("dev_vergilyn", druidDataSource, ruleConfigs, prop.getProps());
	}


	@Bean
	public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("shardingSphereDataSource") DataSource shardingDataSource) throws Exception {
		MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
		VFS.addImplClass(SpringBootVFS.class);

		// bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(
		// 		"classpath:common/owl-sharding-mybatis.xml"));

		bean.setDataSource(shardingDataSource);
		// bean.setMapperLocations(resolveMapperLocations());

		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		bean.setPlugins(interceptor);
		return bean.getObject();
	}

	@Bean
	public DataSourceTransactionManager shardingTransactionManager(
			@Qualifier("shardingSphereDataSource") DataSource shardingDataSource) {
		return new DataSourceTransactionManager(shardingDataSource);
	}

	@Bean
	public SqlSessionTemplate shardingSqlSessionTemplate(
			@Qualifier("shardingSqlSessionFactory") SqlSessionFactory shardingSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(shardingSqlSessionFactory);
	}
}
