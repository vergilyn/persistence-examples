package com.vergilyn.examples.mongodb.document;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * @author vergilyn
 * @since 2021-04-16
 */
@Setter
@Getter
public abstract class AbstractDoc<ID> implements Serializable {

	@Id
	private ID id;
}
