package com.vergilyn.examples.mybatis.annotation;

import org.apache.ibatis.jdbc.SQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author vergilyn
 * @since 2021-02-20
 *
 * @see <a href="https://mybatis.org/mybatis-3/statement-builders.html">The SQL Builder Class</a>
 */
public class Mybatis3SqlBuilderTest {

	@Test
	public void sqlBuilder(){
		String exceptedSQL = "SELECT P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME, " +
				"P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON " +
				"FROM PERSON P, ACCOUNT A " +
				"INNER JOIN DEPARTMENT D on D.ID = P.DEPARTMENT_ID " +
				"INNER JOIN COMPANY C on D.COMPANY_ID = C.ID " +
				"WHERE (P.ID = A.ID AND P.FIRST_NAME like ?) " +
				"OR (P.LAST_NAME like ?) " +
				"GROUP BY P.ID " +
				"HAVING (P.LAST_NAME like ?) " +
				"OR (P.FIRST_NAME like ?) " +
				"ORDER BY P.ID, P.FULL_NAME";

		/*
		 * `{{ }}`语法：https://www.oschina.net/question/2830476_2274044
		 * 即`Double Brace Initialization` 匿名子类初始化，会导致内部类class文件的产生，性能相对标准语法较低。
		 *
		 * 等价于标准语法：
		 * ```
		 * SQL sqlBuilder = new SQL();
		 * sqlBuilder.SELECT(...);
		 * sqlBuilder.SELECT(...);
		 * sqlBuilder.FROM(...);
		 * ```
		 */
		SQL sqlBuilder = new SQL() {{  // 不推荐该语法。
			SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
			SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
			FROM("PERSON P");
			FROM("ACCOUNT A");
			INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
			INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
			WHERE("P.ID = A.ID");
			WHERE("P.FIRST_NAME like ?");
			OR();
			WHERE("P.LAST_NAME like ?");
			GROUP_BY("P.ID");
			HAVING("P.LAST_NAME like ?");
			OR();
			HAVING("P.FIRST_NAME like ?");
			ORDER_BY("P.ID");
			ORDER_BY("P.FULL_NAME");
		}};

		String actualSQL = sqlBuilder.toString();

		System.out.println(actualSQL);
		Assertions.assertEquals(exceptedSQL,
								actualSQL.replaceAll("\\s", " ").replaceAll("\\s{2,}", " "));
	}
}
