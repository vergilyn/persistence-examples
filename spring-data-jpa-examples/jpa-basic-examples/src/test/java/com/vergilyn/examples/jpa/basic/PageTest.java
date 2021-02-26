package com.vergilyn.examples.jpa.basic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import com.vergilyn.examples.jpa.basic.entity.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.PagingBasicRepository;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author vergilyn
 * @since 2021-02-26
 */
@SuppressWarnings("JavadocReference")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PageTest extends AbstractJpaBasicApplicationTest{

	@Autowired
	private PagingBasicRepository repository;

	private static long _total = 10L;

	@BeforeAll
	public void beforeAll(){
		truncateTable(TABLE_NAME);

		List<JpaBasicEntity> entities = Stream.generate(() -> JpaBasicEntity.build(null))
												.limit(_total)
												.collect(Collectors.toList());

		repository.saveAll(entities);
	}

	@Test
	public void page(){
		Sort sort = Sort.by(Sort.Order.desc("id"));
		PageRequest pageRequest = PageRequest.of(0, 2, sort);

		Page<JpaBasicEntity> entities = repository.findAll(pageRequest);

		assertThat(entities.getTotalElements()).isEqualTo(_total);
		assertThat(entities.getContent().get(0).getId()).isEqualTo(_total);
		assertThat(entities.getContent().get(1).getId()).isEqualTo(_total - 1);

		System.out.println("page >>>> " + toJSONString(entities));
	}

	/**
	 * 动态条件分页查询。
	 *
	 * <p>
	 * JPA实现逻辑是先SELECT，(SELECT > 0)再COUNT！
	 * （以前的思路都是先COUNT，如果`COUNT > 0`再SELECT，否则不执行SELECT）
	 *
	 * 思考后:
	 * 更赞成，先SELECT再COUNT，因为主要期望的是SELECT的结果，而不是COUNT的结果。
	 * 另外COUNT语句可能是通过SELECT语句解析出来的，（编码时）能保证SELECT的正确，但不一定能保证解析的COUNT语法正确。
	 * 所以，如果先COUNT，若`COUNT = 0`，但`SELECT > 0`，比较不能接受。反之，相对更能接受。
	 *
	 * @see SimpleJpaRepository#readPage(TypedQuery, Class, Pageable, Specification)
	 * @see TypedQuery#getResultList()
	 * @see SimpleJpaRepository#getCountQuery(org.springframework.data.jpa.domain.Specification, java.lang.Class)
	 */
	@Test
	public void jpaSpecificationExecutor(){
		Sort sort = Sort.by(Sort.Order.desc("id"));
		PageRequest pageRequest = PageRequest.of(0, 2, sort);

		Long geId = 5L;
		String namePrefix = "jpa";

		// expected-sql: WHERE id >= 5 AND name LIKE "%jpa"
		Specification<JpaBasicEntity> specification = (root, query, criteriaBuilder) -> {
			List<Predicate> list = Lists.newArrayList();

			if (geId != null){
				Predicate p1 = criteriaBuilder.greaterThanOrEqualTo(root.get("id"), geId);
				list.add(p1);
			}

			if (StringUtils.isNotBlank(namePrefix)){
				Predicate p2 = criteriaBuilder.like(root.get("name"), namePrefix + "%");
				list.add(p2);
			}

			return criteriaBuilder.and(list.toArray(new Predicate[0]));
		};

		/* mysql-log:
		 *   SET autocommit=0
		 *   SELECT * FROM jpa_basic WHERE id>=5 AND (name like 'jpa%') ORDER BY id DESC LIMIT 2
		 *   SELECT COUNT(id) FROM jpa_basic WHERE id>=5 AND (name LIKE 'jpa%')
		 *   commit
		 */
		Page<JpaBasicEntity> page = repository.findAll(specification, pageRequest);
		assertThat(page.getTotalElements()).isEqualTo(6L);
		assertThat(page.getContent()).matches(entities ->
			entities.size() == 2 && entities.get(0).getId().equals(_total) && entities.get(1).getId().equals(_total - 1)
		);

		System.out.println(toJSONString(page));
	}
}
