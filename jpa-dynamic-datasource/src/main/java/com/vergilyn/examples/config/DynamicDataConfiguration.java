package com.vergilyn.examples.config;


import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.google.common.collect.Maps;
import com.vergilyn.examples.config.dynamic.DynamicDataSourceContextHolder;
import com.vergilyn.examples.config.dynamic.DynamicRoutingDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author VergiLyn
 * @date 2019-03-25
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = "com.vergilyn.examples.repository")
public class DynamicDataConfiguration {

    public enum DataSourceKey{
        master,
        slaveAlpha,
        slaveBeta
    }


    @Primary
    @Bean("master")
    @ConfigurationProperties(prefix = "vergilyn.dynamic-datasource.master.hikari")
    public DataSource master() {
        return DataSourceBuilder.create().build();
    }

    @Bean("slaverAlpha")
    @ConfigurationProperties(prefix = "vergilyn.dynamic-datasource.slaver-alpha.hikari")
    public DataSource slaverAlpha() {
        return DataSourceBuilder.create().build();

    }

    @Bean("slaverBeta")
    @ConfigurationProperties(prefix = "vergilyn.dynamic-datasource.slaver-beta.hikari")
    public DataSource slaverBeta() {
        return DataSourceBuilder.create().build();
    }

    /**
     * FIXME 代码写法待优化
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(
            @Qualifier("master") DataSource master,
            @Qualifier("slaverAlpha") DataSource alpha,
            @Qualifier("slaverBeta") DataSource beta) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(DataSourceKey.master.name(), master);
        dataSourceMap.put(DataSourceKey.slaveAlpha.name(), alpha);
        dataSourceMap.put(DataSourceKey.slaveBeta.name(), beta);

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(master);
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.DATASOURCE_KEYS.addAll(dataSourceMap.keySet());

        // To put slave datasource keys into DataSourceContextHolder to load balance
        DynamicDataSourceContextHolder.SLAVER_DATASOURCE_KEYS.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.SLAVER_DATASOURCE_KEYS.remove(DataSourceKey.master.name());
        return dynamicRoutingDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("dynamicDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(dataSource)
                .packages("com.vergilyn.examples.entity")
//                .persistenceUnit(properties.getPersistenceUnit())
                .build();
//        entityManagerFactory.setJpaPropertyMap(properties.getJpa().getProperties());
        return entityManagerFactory;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
