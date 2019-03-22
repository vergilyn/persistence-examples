package com.vergilyn.examples.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(transactionManagerRef = "firstlyTransactionManager",
        entityManagerFactoryRef = "firstlyEntityManagerFactory",
        basePackages = "com.vergilyn.examples.repository.firstly")
public class FirstlyDatasourceConfiguration extends AbstractMultiDatasource {

    @Bean("firstlyDataSourceProperties")
    @Primary
    public MultiDatasourceProperties firstlyProperties(){
        return new MultiDatasourceProperties();
    }

    @Override
    @Primary
    @Bean(name = "firstlyDataSource")
    public DataSource dataSource(@Qualifier("firstlyDataSourceProperties") MultiDatasourceProperties properties) {
        return super.dataSource(properties);
    }

    @Override
    @Primary
    @Bean(name = "firstlyJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("firstlyDataSource") DataSource dataSource) {
        return super.jdbcTemplate(dataSource);
    }

    @Override
    @Primary
    @Bean(name = "firstlyNamedJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("firstlyJdbcTemplate") JdbcTemplate jdbcTemplate) {
        return super.namedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    @Primary
    @Bean(name = "firstlyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("firstlyDataSource") DataSource dataSource,
                                                                           @Qualifier("firstlyDataSourceProperties") MultiDatasourceProperties properties) {
        return super.entityManagerFactoryBean(builder, dataSource, properties);
    }

    @Override
    @Primary
    @Bean(name = "firstlyTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return super.transactionManager(entityManagerFactory);
    }

}
