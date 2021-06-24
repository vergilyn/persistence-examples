package com.vergilyn.examples.jpa.basic.entity.snowflow;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.vergilyn.examples.jpa.basic.entity.AbstractIdEntity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author vergilyn
 * @since 2021-06-24
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractSnowflowIdEntity extends AbstractIdEntity<String> {

	@Override
	@Column(name = FIELD_ID)
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(generator = "snowflowIdGenerator")
	@GenericGenerator(name = "snowflowIdGenerator", strategy = "com.vergilyn.examples.jpa.basic.generator.SnowflowIdGenerator")
	public String getId() {
		return super.id;
	}
}
