package com.vergilyn.examples.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author VergiLyn
 * @date 2019-03-22
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "secondlyTransactionManager",
        entityManagerFactoryRef = "secondlyEntityManagerFactory",
        basePackages = "com.vergilyn.examples.repository.secondly")
public class SecondlyDatasourceConfiguration extends AbstractMultiDatasource {

    @Bean("secondlyDataSourceProperties")
    @ConfigurationProperties(prefix = "vergilyn.secondly")
    public MultiDatasourceProperties secondlyProperties(){
        return new MultiDatasourceProperties();
    }

    @Override
    @Bean(name = "secondlyDataSource")
    public DataSource dataSource(@Qualifier("secondlyDataSourceProperties") MultiDatasourceProperties properties) {
        return super.dataSource(properties);
    }

    @Override
    @Bean(name = "secondlyJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("secondlyDataSource") DataSource dataSource) {
        return super.jdbcTemplate(dataSource);
    }

    @Override
    @Bean(name = "secondlyNamedJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("secondlyJdbcTemplate") JdbcTemplate jdbcTemplate) {
        return super.namedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    @Bean(name = "secondlyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("secondlyDataSource") DataSource dataSource,
                                                                           @Qualifier("secondlyDataSourceProperties") MultiDatasourceProperties properties) {
        return super.entityManagerFactoryBean(builder, dataSource, properties);
    }

    @Override
    @Bean(name = "secondlyTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return super.transactionManager(entityManagerFactory);
    }

}
