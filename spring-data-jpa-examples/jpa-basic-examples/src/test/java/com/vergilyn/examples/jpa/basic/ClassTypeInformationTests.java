package com.vergilyn.examples.jpa.basic;

import com.vergilyn.examples.jpa.basic.entity.numeric.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CrudBasicRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;

public class ClassTypeInformationTests {

	/**
	 * @see DefaultRepositoryMetadata#DefaultRepositoryMetadata(java.lang.Class)
	 * @see org.springframework.core.GenericTypeResolver
	 */
	@Test
	public void getDomainType(){
		DefaultRepositoryMetadata metadata = new DefaultRepositoryMetadata(CrudBasicRepository.class);

		Assertions.assertThat(metadata.getDomainType()).isSameAs(JpaBasicEntity.class);
		Assertions.assertThat(metadata.getIdType()).isSameAs(Long.class);

	}
}
