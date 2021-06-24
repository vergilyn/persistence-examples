package com.vergilyn.examples.jpa.basic.entity.numeric;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sun.istack.Nullable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = JpaSoftDeleteEntity.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE " + JpaSoftDeleteEntity.TABLE_NAME
					+ " SET " + AbstractLongEntity.FIELD_IS_DELETED + " = true"
					+ " WHERE " + AbstractLongEntity.FIELD_ID + " = ?")
@Where(clause = AbstractLongEntity.SOFT_DELETE_SQL) // soft-delete, not-support defined in super-class(e.g. `AbstractEntity`)
public class JpaSoftDeleteEntity extends AbstractLongEntity{

	public static final String TABLE_NAME = "jpa_soft_delete";

	private String name;

	public JpaSoftDeleteEntity(Long id) {
		setId(id);
	}

	public static JpaSoftDeleteEntity build(@Nullable Long id){
		JpaSoftDeleteEntity entity = new JpaSoftDeleteEntity(id);
		entity.setId(id);
		entity.setCreateTime(LocalDateTime.now());
		entity.setModifyTime(entity.getCreateTime());
		entity.setIsDeleted(false);

		entity.setName("jpa-soft-delete-default-" + id);
		return entity;
	}
}
