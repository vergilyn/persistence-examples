package com.vergilyn.examples.jpa.basic;

import java.util.Optional;

import com.vergilyn.examples.jpa.AbstractJpaBasicApplicationTest;
import com.vergilyn.examples.jpa.basic.entity.numeric.JpaSoftDeleteEntity;
import com.vergilyn.examples.jpa.basic.repository.JpaSoftDeleteRepository;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.testng.collections.Lists;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. soft-delete 时，都是先SELECT再UPDATE。e.g. {@linkplain #softDelete()}、{@linkplain #softDeleteAll()} <br/>
 */
public class JpaSoftDeleteCrudTest extends AbstractJpaBasicApplicationTest {

	@Autowired
	private JpaSoftDeleteRepository softDeleteRepository;

	private static Long _id;
	private static Long _soft_delete_id;

	@BeforeEach
	public void beforeEach(){
		truncateTable(JpaSoftDeleteEntity.TABLE_NAME);

		JpaSoftDeleteEntity entity = softDeleteRepository.save(JpaSoftDeleteEntity.build(null));
		_id = entity.getId();

		JpaSoftDeleteEntity softDeleteEntity = JpaSoftDeleteEntity.build(null);
		softDeleteEntity.setIsDeleted(true);
		softDeleteEntity = softDeleteRepository.save(softDeleteEntity);
		_soft_delete_id = softDeleteEntity.getId();
	}

	@Test
	public void softDelete(){
		/* 正确 soft-delete
		 *
		 * LOG:
		 *   Hibernate: select * from jpa_soft_delete where id=? and (is_deleted = 0)
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 */
		JpaSoftDeleteEntity e1 = JpaSoftDeleteEntity.build(null);
		e1 = softDeleteRepository.save(e1);
		softDeleteRepository.delete(e1);

		/* 正确 soft-delete
		 *
		 * LOG:
		 *   Hibernate: select * from jpa_soft_delete where .id=? and (is_deleted = 0)
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 */
		JpaSoftDeleteEntity e2 = JpaSoftDeleteEntity.build(null);
		e2 = softDeleteRepository.save(e2);
		softDeleteRepository.deleteById(e2.getId());
	}

	@Test
	public void softDeleteAll(){
		JpaSoftDeleteEntity e1 = JpaSoftDeleteEntity.build(null);
		JpaSoftDeleteEntity e2 = JpaSoftDeleteEntity.build(null);
		JpaSoftDeleteEntity e3 = JpaSoftDeleteEntity.build(null);

		softDeleteRepository.saveAll(Lists.newArrayList(e1, e2, e3));

		/* 正确soft-delete，但通过log可知，其性能可能不高。
		 *   先查询所有数据（并且返回的是所有列，而不是只返回ID列），正确携带了soft-query `is_deleted = 0`，然后循环delete。
		 *
		 * 打印LOG:
		 *   Hibernate: select * from jpa_soft_delete where (is_deleted = 0)
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 *   Hibernate: UPDATE jpa_soft_delete SET is_deleted = true WHERE id = ?
		 *
		 * mysql-log:
		 *  SET autocommit=0;
	     *  select * from jpa_soft_delete where (is_deleted = 0);
	     *  UPDATE jpa_soft_delete SET is_deleted = true WHERE id = 1;
	     *  UPDATE jpa_soft_delete SET is_deleted = true WHERE id = 3;
	     *  UPDATE jpa_soft_delete SET is_deleted = true WHERE id = 4;
	     *  UPDATE jpa_soft_delete SET is_deleted = true WHERE id = 5;
		 *  commit;
		 */
		softDeleteRepository.deleteAll();
	}

	/**
	 * {@linkplain JpaSoftDeleteEntity}: `@Where(clause = "is_deleted = false")`
	 *   soft-delete, not-support defined in super-class(e.g. `AbstractEntity`)
	 *
	 * <p>
	 * 不太建议使用这种方式（可能存在某些查询情况无法正确拼接`is_deleted = false`），还是建议在查询方法中指定查询条件实现。
	 */
	@Test
	public void findSoftDelete(){
		// 正确拼接`is_deleted = false`
		Optional<JpaSoftDeleteEntity> none = softDeleteRepository.findById(_soft_delete_id);
		assertThat(none.isPresent()).isFalse();

		// 正确拼接`is_deleted = false`
		Iterable<JpaSoftDeleteEntity> all = softDeleteRepository.findAll();
		assertThat(all).areNot(new Condition<>(e -> e.getId().equals(_soft_delete_id),
				"expected not found: id = " + _soft_delete_id));

		// 正确拼接`is_deleted = false`
		Page<JpaSoftDeleteEntity> page = softDeleteRepository.findAll(PageRequest.of(0, 10));
		assertThat(page.getTotalElements()).isEqualTo(1L);
		assertThat(page.getContent()).doesNotHave(new Condition<>(entities -> {
			for (JpaSoftDeleteEntity entity : entities) {
				if (entity.getId().equals(_soft_delete_id)) {
					return true;
				}
			}
			return false;
		}, "expected not found: id = " + _soft_delete_id));

		// 正确拼接`is_deleted = false`
		Optional<JpaSoftDeleteEntity> one = softDeleteRepository
				.findOne((Specification<JpaSoftDeleteEntity>) (root, query, criteriaBuilder) -> {
					return criteriaBuilder.equal(root.get("id"), _soft_delete_id);
				});
		assertThat(one.isPresent()).isFalse();
	}
}
