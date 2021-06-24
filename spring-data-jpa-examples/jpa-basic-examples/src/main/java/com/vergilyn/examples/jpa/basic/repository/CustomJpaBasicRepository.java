package com.vergilyn.examples.jpa.basic.repository;

import com.vergilyn.examples.jpa.basic.entity.numeric.JpaBasicEntity;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * "The most important part of the class name that corresponds to the fragment interface
 *      is the `Impl`({@linkplain EnableJpaRepositories#repositoryImplementationPostfix()}) postfix."
 *
 * @author vergilyn
 * @since 2021-02-26
 *
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/2.4.5/reference/html/#repositories.custom-implementations">
 *          Custom Implementations for Spring Data Repositories</a>
 */
public interface CustomJpaBasicRepository {

	JpaBasicEntity findByJdbcTemplate(Long id);

	JpaBasicEntity findByNamedTemplate(Long id);

	int updateByJdbcTemplate(Long id, String name);

	int updateByNamedTemplate(Long id, String name);
}
