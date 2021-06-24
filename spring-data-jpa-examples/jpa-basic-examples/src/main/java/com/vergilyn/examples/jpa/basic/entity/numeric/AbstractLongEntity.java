package com.vergilyn.examples.jpa.basic.entity.numeric;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import com.alibaba.fastjson.JSON;
import com.vergilyn.examples.jpa.basic.entity.AbstractIdEntity;

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
 * @see org.hibernate.annotations.GenericGenerator
 */
@Setter
@Getter
@MappedSuperclass
public abstract class AbstractLongEntity extends AbstractIdEntity<Long> {
	protected static final String FIELD_IS_DELETED = "is_deleted";
	protected static final String SOFT_DELETE_SQL = FIELD_IS_DELETED + " = false";


	// @org.hibernate.annotations.CreationTimestamp
	private LocalDateTime createTime;
	// @org.hibernate.annotations.UpdateTimestamp
	private LocalDateTime modifyTime;

	@Column(name = FIELD_IS_DELETED)
	private Boolean isDeleted;

	/**
	 * {@linkplain GenerationType}:
	 * <pre>
	 *   TABLE：使用一个特定的数据库表格来保存主键。
	 *   SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
	 *   IDENTITY：主键由数据库自动生成（主要是自动增长型）
	 *   AUTO：主键由程序控制。(默认是`SEQUENCE`，会生成"table: hibernate_sequence")
	 * </pre>
	 *
	 * <br/>
	 *
	 * {@linkplain GenericGenerator#strategy()}: {@linkplain DefaultIdentifierGeneratorFactory}
	 */
	@Override
	@Column(name = FIELD_ID)
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.id;
	}

	@Override
	public String toString() {
		return Integer.toHexString(hashCode()) + "@" + JSON.toJSONString(this, WriteNullStringAsEmpty);
	}

}
