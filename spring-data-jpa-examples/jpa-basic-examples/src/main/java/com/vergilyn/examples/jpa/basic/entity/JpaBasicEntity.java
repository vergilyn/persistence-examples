package com.vergilyn.examples.jpa.basic.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.sun.istack.Nullable;
import com.vergilyn.examples.jpa.basic.enums.JpaEnum;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = JpaBasicEntity.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JpaBasicEntity extends AbstractEntity<Long>{

	public static final String TABLE_NAME = "jpa_basic";

	private String name;

	@Enumerated(EnumType.STRING)
	private JpaEnum enumField;

	public JpaBasicEntity(Long id) {
		setId(id);
	}

	public static JpaBasicEntity build(@Nullable Long id){
		JpaBasicEntity entity = new JpaBasicEntity(id);
		entity.setId(id);
		entity.setCreateTime(LocalDateTime.now());
		entity.setModifyTime(entity.getCreateTime());
		entity.setIsDeleted(false);

		entity.setName("jpa-basic-default-" + id);
		entity.setEnumField(JpaEnum.FIRST);

		return entity;
	}
}
