package com.vergilyn.examples.shardingsphere.bugs;

import io.shardingsphere.core.constant.DatabaseType;
import io.shardingsphere.core.metadata.table.ShardingTableMetaData;
import io.shardingsphere.core.parsing.lexer.LexerEngine;
import io.shardingsphere.core.parsing.parser.clause.WhereClauseParser;
import io.shardingsphere.core.parsing.parser.dialect.mysql.sql.MySQLUpdateParser;
import io.shardingsphere.core.parsing.parser.expression.SQLExpression;
import io.shardingsphere.core.parsing.parser.sql.SQLParserFactory;
import io.shardingsphere.core.parsing.parser.sql.SQLStatement;
import io.shardingsphere.core.parsing.parser.sql.dml.DMLStatement;
import io.shardingsphere.core.parsing.parser.sql.dml.update.AbstractUpdateParser;
import io.shardingsphere.core.routing.PreparedStatementRoutingEngine;
import io.shardingsphere.core.routing.router.sharding.ParsingSQLRouter;
import io.shardingsphere.core.rule.ShardingRule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

/**
 * 假设存在分表 `short_link_{0, 15}`，根据主键 `link_id` 分表。
 * <pre> Logic SQL:
 *   UPDATE
 *         short_link
 *   SET
 *         update_time = now(),
 *         read_number = ifnull(read_number, 0) +
 *           CASE
 *               WHEN link_id=? THEN ?
 *               WHEN link_id=? THEN ?
 *               WHEN link_id=? THEN ?
 *               WHEN link_id=? THEN ?
 *               WHEN link_id=? THEN ?
 *             ELSE 0
 *           END
 *     WHERE link_id IN (?, ?, ?, ?, ?)
 *
 *
 *  对应参数列表 parameters：
 *    893562577903177728L,
 *    1,
 *    892827629193809920L,
 *    2,
 *    892835337292763136L,
 *    3,
 *    892853112220438529L,
 *    4,
 *    892853535291494400L,
 *    5,
 *    893562577903177728L,
 *    892827629193809920L,
 *    892835337292763136L,
 *    892853112220438529L,
 *    892853535291494400L
 *
 *  ID分表情况
 *   short_link_1: 892827629193809920
 *   short_link_2: 892835337292763136
 *   short_link_3: 892853112220438529
 *   short_link_14: 893562577903177728
 *   short_link_15: 892853535291494400
 * </pre>
 *
 * <p> <b>问题：</b> `sharding-3.1.0`中最后取的是 `link_id = [893562577903177728L, 1, 892827629193809920L, 2, 892835337292763136L]` 去路由，其中的`1 & 2`
 * 指的并不是 `link_id`。
 *
 * <p> <b>期望：</b> 正确的分表ID是取 parameters[893562577903177728L, 892827629193809920L, 892835337292763136L, 892853112220438529L, 892853535291494400L]
 *
 * @author vergilyn
 * @since 2023-08-24
 */
@SuppressWarnings("JavadocReference")
public class LogicSQLRouteBugTest {

    /**
     * 核心错误是 {@link PreparedStatementRoutingEngine#route(List)} 中, 解析 {@code logicSQL} 得到的 sqlStatement 信息存在错误。
     *
     * <pre>
     *     {@link AbstractUpdateParser#parse} 中由于是 {@code `DMLStatement result = new DMLStatement()`}
     *     导致 `{@link DMLStatement#parametersIndex} = 0`，从而导致最终是获取 parameters[0~5] 的参数值去分表路由
     *
     *     - {@link MySQLUpdateParser}
     *     - {@link WhereClauseParser#parse(ShardingRule, SQLStatement, List)}
     *     - {@link WhereClauseParser#parseOr(ShardingRule, SQLStatement, List)}
     *     - {@link WhereClauseParser#parseAnd(ShardingRule, SQLStatement, List)}
     *     - {@link WhereClauseParser#parseInCondition(ShardingRule, SQLStatement, SQLExpression)}
     * </pre>
     *
     * <h2>解决方式</h2>
     * <p> 方式一：不改变SQL结构，将sql中的 case-when参数从`#{var}` 改成 `${var}`。即不用占位符`?`，直接拼接值。
     * （存在SQL注入风险）
     *
     * @see SQLParserFactory#newInstance(DatabaseType, ShardingRule, LexerEngine, ShardingTableMetaData, String)
     */
    @Test
    void reproduceBug(){
        ShardingRule shardingRule = Mockito.mock(ShardingRule.class);

        // 好难复现测试...部分对象太难构建了..
        ParsingSQLRouter parsingSQLRouter = new ParsingSQLRouter(shardingRule, null, DatabaseType.MySQL, true);

        String CRLF = "\n";
        String logicSQL = "UPDATE" + CRLF
                          + "      short_link" + CRLF
                          + "SET" + CRLF
                          + "      update_time = now()," + CRLF
                          + "      read_number = ifnull(read_number, 0) +" + CRLF
                          + "        CASE" + CRLF
                          + "            WHEN link_id=? THEN ?" + CRLF
                          + "            WHEN link_id=? THEN ?" + CRLF
                          + "            WHEN link_id=? THEN ?" + CRLF
                          + "            WHEN link_id=? THEN ?" + CRLF
                          + "            WHEN link_id=? THEN ?" + CRLF
                          + "          ELSE 0" + CRLF
                          + "        END" + CRLF
                          + "  WHERE link_id IN (?, ?, ?, ?, ?)";

        SQLStatement sqlStatement = parsingSQLRouter.parse(logicSQL, true);

        System.out.println(sqlStatement);
    }
}
