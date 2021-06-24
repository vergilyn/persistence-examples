package com.vergilyn.examples.jpa.basic.generator;

import java.io.Serializable;
import java.util.Properties;

import com.vergilyn.examples.jpa.basic.entity.snowflow.AbstractSnowflowIdEntity;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

/**
 *
 * @author vergilyn
 * @since 2021-06-24
 *
 * @see org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory
 */
public class SnowflowIdGenerator implements Configurable, IdentifierGenerator {

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {

	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		AbstractSnowflowIdEntity snowflowIdEntity = (AbstractSnowflowIdEntity) object;

		final String id = snowflowIdEntity.getId();
		if (id == null){
			return SeataUUIDGenerator.generateUUID() + "";
		}

		return id;
	}
}
