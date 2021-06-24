package com.vergilyn.examples.jpa.cascade.simplex;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.vergilyn.examples.jpa.cascade.AbstractJpaCascadeApplicationTestng;
import com.vergilyn.examples.jpa.cascade.entity.UserOrderSimplexEntity;
import com.vergilyn.examples.jpa.cascade.entity.UserSimplexEntity;
import com.vergilyn.examples.jpa.cascade.repository.UserOrderSimplexRepository;
import com.vergilyn.examples.jpa.cascade.repository.UserSimplexRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class OneToManySimplexTests extends AbstractJpaCascadeApplicationTestng {

	@Autowired
	UserSimplexRepository userSimplexRepository;
	@Autowired
	UserOrderSimplexRepository orderRepository;

	protected Long _userId;

	/**
	 * <pre>
	 *   Hibernate: insert into jpa_cascade_user (create_time, is_deleted, modify_time, nickname, username) values (?, ?, ?, ?, ?)
	 *   Hibernate: insert into jpa_cascade_user_order (amount, title) values (?, ?)
	 *   Hibernate: insert into jpa_cascade_user_order (amount, title) values (?, ?)
	 *   Hibernate: update jpa_cascade_user_order set user_id=? where id=?
	 *   Hibernate: update jpa_cascade_user_order set user_id=? where id=?
	 *   Hibernate: select ...
	 * </pre>
	 */
	@Test
	// @Transactional
	public void save(){
		UserSimplexEntity user = new UserSimplexEntity("username", "nickname");

		UserOrderSimplexEntity order1 = new UserOrderSimplexEntity(user.getUsername() + "-100", 100);
		UserOrderSimplexEntity order2 = new UserOrderSimplexEntity(user.getUsername() + "-200", 200);
		user.setOrders(Lists.newArrayList(order1, order2));

		userSimplexRepository.save(user);  // committed

		_userId = user.getId();

		UserSimplexEntity find = userSimplexRepository.findById(_userId).get();
		System.out.println(JSON.toJSONString(find, true));

	}

	@Test(dependsOnMethods = "save")
	public void update(){
		
	}

	/**
	 * （）
	 * <pre>
	 *   Hibernate: SELECT * FROM jpa_cascade_user u left outer join jpa_cascade_user_order o on u.id=o.user_id where u.id=?
	 *   Hibernate: update jpa_cascade_user_order set user_id=null where user_id=?
	 *   Hibernate: delete from jpa_cascade_user_order where id=?
	 *   Hibernate: delete from jpa_cascade_user_order where id=?
	 *   Hibernate: delete from jpa_cascade_user where id=?
	 *   Hibernate: select ...  // findByUserId
	 * </pre>
	 */
	@Test(dependsOnMethods = "save")
	public void delete(){
		userSimplexRepository.deleteById(_userId);

		List<UserOrderSimplexEntity> orders = orderRepository.findByUserId(_userId);
		System.out.println(JSON.toJSONString(orders, true));
	}

}
