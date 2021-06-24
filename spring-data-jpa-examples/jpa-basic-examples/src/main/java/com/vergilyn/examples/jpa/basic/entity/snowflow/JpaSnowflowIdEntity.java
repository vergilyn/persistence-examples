package com.vergilyn.examples.jpa.basic.entity.snowflow;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jpa_snowflow_id")
@Setter
@Getter
@NoArgsConstructor
public class JpaSnowflowIdEntity extends AbstractSnowflowIdEntity{

	private String name;

	public JpaSnowflowIdEntity(String name) {
		this.name = name;
	}

	public JpaSnowflowIdEntity(String id, String name) {
		setId(id);
		this.name = name;
	}
}
