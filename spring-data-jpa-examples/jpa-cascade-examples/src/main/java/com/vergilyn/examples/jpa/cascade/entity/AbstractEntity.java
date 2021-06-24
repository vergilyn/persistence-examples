package com.vergilyn.examples.jpa.cascade.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;

/**
 *
 * {@linkplain GenericGenerator#strategy()} SEE: {@linkplain DefaultIdentifierGeneratorFactory}
 *
 *
 * @author vergilyn
 * @since 2021-02-25
 *
 * @see Persistable
 * @see AbstractPersistable
 * @see org.springframework.data.jpa.domain.AbstractAuditable
 * @see GenericGenerator
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> extends AbstractIdEntity<ID> {
	protected static final String FIELD_IS_DELETED = "is_deleted";
	protected static final String SOFT_DELETE_SQL = FIELD_IS_DELETED + " = false";

	@org.hibernate.annotations.CreationTimestamp
	private LocalDateTime createTime;
	@org.hibernate.annotations.UpdateTimestamp
	private LocalDateTime modifyTime;

	@Column(name = FIELD_IS_DELETED)
	private boolean isDeleted = false;

	@Override
	public String toString() {
		return Integer.toHexString(hashCode()) + "@" + JSON.toJSONString(this, WriteNullStringAsEmpty);
	}

}
