package com.vergilyn.examples.jpa.cascade.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jpa_cascade_simplex_user")
@Setter
@Getter
@NoArgsConstructor
public class UserSimplexEntity extends AbstractEntity<Long> {

	private String username;

	private String nickname;

	@OneToMany(
			fetch = FetchType.EAGER // VFIXME 2021-06-22 `LAZY`
			, cascade = { CascadeType.ALL}
			, orphanRemoval = true  // "孤儿删除"
	)
	// 单向关系，最终SQL会 “先insert再update-FK” （code-example 可以看`@OneToMany` javadocs）
	@JoinColumn(name = "userId", referencedColumnName = FIELD_ID)
	private List<UserOrderSimplexEntity> orders;

	public UserSimplexEntity(String username, String nickname) {
		this.username = username;
		this.nickname = nickname;
	}
}
