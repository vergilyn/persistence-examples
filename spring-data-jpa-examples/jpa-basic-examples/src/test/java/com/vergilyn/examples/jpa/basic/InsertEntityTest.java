package com.vergilyn.examples.jpa.basic;

import java.util.stream.Stream;

import javax.persistence.GeneratedValue;

import com.vergilyn.examples.jpa.AbstractJpaBasicApplicationTest;
import com.vergilyn.examples.jpa.basic.entity.numeric.JpaBasicEntity;
import com.vergilyn.examples.jpa.basic.repository.CrudBasicRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author vergilyn
 * @since 2021-02-25
 */
@SuppressWarnings("JavadocReference")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InsertEntityTest extends AbstractJpaBasicApplicationTest {

	@Autowired
	private CrudBasicRepository repository;

	private static long _autoIncrement = 10;

	@BeforeAll
	public void beforeAll(){
		// 清空数据
		truncateTable(TABLE_NAME);
		// 修改 auto_increment
		jdbcTemplate.execute("ALTER TABLE " + TABLE_NAME + " AUTO_INCREMENT = " + _autoIncrement);
	}

	@Test
	public void save(){
		Long id = null;
		JpaBasicEntity e = JpaBasicEntity.build(id);

		e = repository.save(e);

		System.out.printf("expected: %d, actual: %d\n", id, e.getId());

		assertThat(e.getId()).isEqualTo(id);
	}

	/**
	 * {@linkplain AbstractEntity#id} -> {@linkplain GeneratedValue#strategy()}:
	 * <pre>
	 *   TABLE: 使用一个特定的数据库表格来保存PK。
	 *   SEQUENCE: 根据底层数据库的序列来生成PK，条件是数据库支持序列。
	 *   IDENTITY: PK由数据库自动生成（主要是自动增长型）。
	 *   AUTO: PK由程序控制。
	 * </pre>
	 *
	 * <p>
	 * 1) `id = null`，由mysql生成。（注意 IDENTITY 与 AUTO 存在区别）。<br/>
	 * 2) `id = 8`，
	 *   {@code exist}，则update。
	 *   {@code not-exist}，则insert，但是最终的PK是`auto_increment`，而不是entity设置的ID。
	 *
	 * @see <a href="https://mp.weixin.qq.com/s/tYnQzmuFeaAmd7CEPAwfig">SpringBoot系列教程JPA之指定id保存</a>
	 */
	@ParameterizedTest
	@MethodSource("ids")
	public void autoIncrement(Long id){
		JpaBasicEntity e1 = JpaBasicEntity.build(id);
		System.out.println("save before >>>> " + e1);

		// norm: `e1 = repository.save(e1);`
		// error: `repository.save(e1);` 如果是情况`2)`，`e1.getId()`的值不正确！
		JpaBasicEntity e2 = repository.save(e1);

		System.out.println("save after >>>> " + e1);
		System.out.println("save after return >>>> " + e2);

		if (id == null){
			JpaBasicEntity f1 = repository.findById(_autoIncrement).get();
			System.out.printf("findById(%d) >>>> %s \n", _autoIncrement, f1);

			assertTrue(e1 == e2);

			assertThat(f1.getId())
					.isEqualTo(_autoIncrement)
					.isEqualTo(e1.getId())
					.isEqualTo(e2.getId());
		}else {
			assertTrue(e1 != e2);

			// excepted: 8, actual: 11
			assertThat(e1.getId())
					.isEqualTo(e2.getId());
		}
	}

	public static Stream<Long> ids(){
		return Stream.of(null, _autoIncrement - 2);
	}
}
