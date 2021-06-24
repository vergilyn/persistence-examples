package com.vergilyn.examples.jpa.cascade.duplex;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.vergilyn.examples.jpa.cascade.AbstractJpaCascadeApplicationTestng;
import com.vergilyn.examples.jpa.cascade.entity.UserDuplexEntity;
import com.vergilyn.examples.jpa.cascade.entity.UserOrderDuplexEntity;
import com.vergilyn.examples.jpa.cascade.repository.UserDuplexRepository;
import com.vergilyn.examples.jpa.cascade.repository.UserOrderDuplexRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class OneToManyDuplexTests extends AbstractJpaCascadeApplicationTestng {

	@Autowired
	UserDuplexRepository userRepository;
	@Autowired
	UserOrderDuplexRepository orderRepository;

	protected Long _userId;

	/**
	 * 对比 `单向` 的好处，SQL不再是“先insert，在update-FK”
	 * <pre>
	 *   Hibernate: insert into jpa_cascade_duplex_user (create_time, is_deleted, modify_time, nickname, username) values (?, ?, ?, ?, ?)
	 *   Hibernate: insert into jpa_cascade_duplex_user_order (amount, title, user_id) values (?, ?, ?)
	 *   Hibernate: insert into jpa_cascade_duplex_user_order (amount, title, user_id) values (?, ?, ?)
	 *   Hibernate: select...
	 * </pre>
	 */
	@Test
	// @Transactional
	public void save(){
		UserDuplexEntity user = new UserDuplexEntity("username", "nickname");

		// 双向必须 子表要设置主表对象，否则子表中的`user_id = null`
		UserOrderDuplexEntity order1 = new UserOrderDuplexEntity(user.getUsername() + "-100", 100);
		order1.setUser(user);

		UserOrderDuplexEntity order2 = new UserOrderDuplexEntity(user.getUsername() + "-200", 200);
		order2.setUser(user);

		// 主表关系也需要，因为最后是用主表的保存。
		user.setOrders(Lists.newArrayList(order1, order2));

		userRepository.save(user);  // committed

		_userId = user.getId();

		UserDuplexEntity find = userRepository.findById(_userId).get();
		System.out.println(JSON.toJSONString(find, true));
	}

	@Test(dependsOnMethods = "save")
	public void update(){
		
	}

	/**
	 * （）
	 * <pre>
	 *   Hibernate: SELECT * FROM jpa_cascade_duplex_user du LEFT OUTER JOIN jpa_cascade_duplex_user_order duo ON du.id=duo.user_id where du.id=?
	 *   Hibernate: delete from jpa_cascade_duplex_user_order where id=?
	 *   Hibernate: delete from jpa_cascade_duplex_user_order where id=?
	 *   Hibernate: delete from jpa_cascade_duplex_user where id=?
	 *   Hibernate: select...  // findByUserId
	 * </pre>
	 */
	@Test(dependsOnMethods = "save")
	public void delete(){
		userRepository.deleteById(_userId);

		List<UserOrderDuplexEntity> orders = orderRepository.findByUserId(_userId);
		System.out.println(JSON.toJSONString(orders, true));
	}

}
