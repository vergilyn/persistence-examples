package com.vergilyn.examples.jpa.cascade.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jpa_cascade_duplex_user")
@Setter
@Getter
@NoArgsConstructor
public class UserDuplexEntity extends AbstractEntity<Long> {

	private String username;

	private String nickname;

	@OneToMany(
			fetch = FetchType.EAGER // VFIXME 2021-06-22 `LAZY`
			, cascade = { CascadeType.ALL}
			, orphanRemoval = true  // "孤儿删除"
			, mappedBy = "user"
	)
	private List<UserOrderDuplexEntity> orders;

	public UserDuplexEntity(String username, String nickname) {
		this.username = username;
		this.nickname = nickname;
	}
}
