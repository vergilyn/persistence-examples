package com.vergilyn.examples.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author VergiLyn
 * @date 2019-03-22
 */
@Component
@ConfigurationProperties("vergilyn.firstly")
public class MultiDatasourceProperties {
    private DataSourceProperties datasource;
    private JpaProperties jpa;
    private String[] packages;
    private String persistenceUnit;

    public DataSourceProperties getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSourceProperties datasource) {
        this.datasource = datasource;
    }

    public JpaProperties getJpa() {
        return jpa;
    }

    public void setJpa(JpaProperties jpa) {
        this.jpa = jpa;
    }

    public String[] getPackages() {
        return packages;
    }

    public void setPackages(String[] packages) {
        this.packages = packages;
    }

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public void setPersistenceUnit(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }
}
