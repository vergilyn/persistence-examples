package com.vergilyn.examples.jpa.basic.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNullStringAsEmpty;


/**
 *
 * @author vergilyn
 * @since 2021-02-25
 *
 * @see Persistable
 * @see AbstractPersistable
 * @see org.springframework.data.jpa.domain.AbstractAuditable
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Serializable {
	protected static final String FIELD_ID = "id";
	protected static final String FIELD_IS_DELETED = "is_deleted";
	protected static final String SOFT_DELETE_SQL = FIELD_IS_DELETED + " = false";

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = FIELD_ID)
	private ID id;

	private LocalDateTime createTime;
	private LocalDateTime modifyTime;

	@Column(name = FIELD_IS_DELETED)
	private Boolean isDeleted;

	@Override
	public String toString() {
		return Integer.toHexString(hashCode()) + "@" + JSON.toJSONString(this, WriteNullStringAsEmpty);
	}

}
