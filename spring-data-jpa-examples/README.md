# jpa-single-datasource

+ <https://docs.spring.io/spring-data/jpa/docs/2.4.5/reference/html/#reference>
+ <https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html>

- 单数据源，基于mysql、hikari
- jpa、JdbcTemplate、NamedParameterJdbcTemplate
- 事务

[spring-boot JPA ](https://docs.spring.io/spring-boot/docs/2.1.2.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

> The NamedParameterJdbcTemplate reuses the same JdbcTemplate instance behind the scenes. 
> If more than one JdbcTemplate is defined and no primary candidate exists, the NamedParameterJdbcTemplate is not auto-configured.

## 1. Entity中并不需要显示的声明`@Column`
``` java
@Entity
@Table(name = "table_name")
@Data
@NoArgsConstructor
@ToString
public class XxxEntity {
}
```

hibernate-5.x开始，不再支持`hibernate.ejb.naming_strategy`，取而代之的是:
```
spring.jpa.hibernate.naming.physical-strategy: 隐式命名策略，使用此属性当我们使用的表或列没有明确指定一个使用的名称 。
spring.jpa.hibernate.naming.implicit-strategy: 物理命名策略，用于转换“逻辑名称”(隐式或显式)的表或列成一个物理名称。
```

对于PhysicalNamingStrategyStandardImpl有DefaultNamingStrategy的效果；
对于SpringPhysicalNamingStrategy有ImprovedNamingStrategy的效果。

- [SpringBoot Jpa使用时碰到的问题总结](https://www.jianshu.com/p/fc2c79814956)

## 2. jpa/hibernate + spring-jdbc 混合，由于缓存引发的"事务问题"

[jpa+spring jdbc混合作战引发的缓存问题]: https://blog.csdn.net/xiejx618/article/details/42130165
[Spring JDBC-混合框架的事务管理]: https://www.cnblogs.com/shitaotao/p/7616336.html
[Spring Data Jpa 缓存]: https://blog.csdn.net/chenjianandiyi/article/details/52261564
[hibernate的一级缓存是没法禁止的,那spring-data-jpa为什么没有缓存呢]: https://www.oschina.net/question/3283783_2265568

- **[Spring JDBC-混合框架的事务管理]**
- [jpa+spring jdbc混合作战引发的缓存问题]
- [Spring Data Jpa 缓存]
- [hibernate的一级缓存是没法禁止的,那spring-data-jpa为什么没有缓存呢]

jpa默认启用一级缓存，即session缓存（org.hibernate.SessionFactory）。

```
// 当前 `email = aaaa`
@Transactional
public void method(Long customerId){
   
    Customer before = getByJpa(customerId);  // jpa的findOne, before.email = aaaa
    
    updateByJPA(customerId, "xxxx");  // jpa的save(entity), 执行后 before.email = xxxx
    
    Customer after = getByJpa(customerId);  // email = xxxx, 且 before == after
    
    Customer jdbc = getByJdbc(customerId);  // JdbcTemplate, email = aaaa 
    
    Customer named = getByNamed(customerId); // NamedParameterJdbcTemplate, email = aaaa 
}

```

1. 为什么 `before.email == after.email`？
因为jpa的session缓存机制（具体实现不清楚）。
并且`before == after`（并未重写Customer的hashcode/equals），由此可知jpa的findOne在同一个session返回的是同一个实例对象。

2. 为什么jdbc/named无法获得更新后的值，难道跟jpa不处于同一个事务中？
首先，jdbc/named确实跟jpa处于同一事务中。
无法获取当前事务中的新值原因**可能是**: jpa此时session并未提交，所以当前事务中`email = aaaa`。


把update改成JdbcTemplate/NamedParameterJdbcTemplate
```
// 当前 `email = aaaa`
@Transactional
public void method(Long customerId){
   
    Customer before = getByJpa(customerId);  // jpa的findOne, before.email = aaaa
    
    updateByJDBC(customerId, "xxxx");  // jdbc执行update-sql, 执行后 before.email = aaaa
    
    Customer after = getByJpa(customerId);  // email = aaaa, 且 before == after
    
    Customer jdbc = getByJdbc(customerId);  // JdbcTemplate, email = xxxx 
    
    Customer named = getByNamed(customerId); // NamedParameterJdbcTemplate, email = xxxx 
}

```


jdbc/named无法感知也不会去操作jpa/hibernate的session，又由于jpa先于update执行了query。
所以由于session-cache，导致after是直接取的session-cache。

若注释掉before查询，则`after.email = xxxx`（数据库还是aaaa，因为事务并未提交）。
所以，这也证明了jpa与jdbc/named确实处于同一个事务中。


验证jpa、jdbc事务的提交顺序

```
    @Transactional
    public void commitSequence(){
        updateByJdbc(customerId, "before");
        updateByJpa(customerId, "after");
    }
```
期望`after`，实际`after`。

```
    @Transactional
    public void commitSequence(){
        updateByJpa(customerId, "before");
        updateByJdbc(customerId, "after");
    }
```
期望`after`，实际`before`。 此执行日志如下
```

DEBUG 5804 --- [           main] stomAnnotationTransactionAttributeSource : Adding transactional method 'findById' with attribute: PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(1589864603<open>)] for JPA transaction
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
DEBUG 5804 --- [           main] org.hibernate.SQL                        : select customer0_.customer_id as customer1_0_0_, customer0_.active as active2_0_0_, customer0_.address_id as address_3_0_0_, customer0_.create_date as create_d4_0_0_, customer0_.email as email5_0_0_, customer0_.first_name as first_na6_0_0_, customer0_.last_name as last_nam7_0_0_, customer0_.last_update as last_upd8_0_0_, customer0_.store_id as store_id9_0_0_ from customer customer0_ where customer0_.customer_id=?
Hibernate: select customer0_.customer_id as customer1_0_0_, customer0_.active as active2_0_0_, customer0_.address_id as address_3_0_0_, customer0_.create_date as create_d4_0_0_, customer0_.email as email5_0_0_, customer0_.first_name as first_na6_0_0_, customer0_.last_name as last_nam7_0_0_, customer0_.last_update as last_upd8_0_0_, customer0_.store_id as store_id9_0_0_ from customer customer0_ where customer0_.customer_id=?
DEBUG 5804 --- [           main] o.h.l.p.e.p.i.ResultSetProcessorImpl     : Starting ResultSet row #0
DEBUG 5804 --- [           main] l.p.e.p.i.EntityReferenceInitializerImpl : On call to EntityIdentifierReaderImpl#resolve, EntityKey was already known; should only happen on root returns with an optional identifier specified
DEBUG 5804 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Resolving associations for [com.vergilyn.examples.entity.Customer#1]
DEBUG 5804 --- [           main] o.h.engine.internal.TwoPhaseLoad         : Done materializing entity [com.vergilyn.examples.entity.Customer#1]
DEBUG 5804 --- [           main] o.h.r.j.i.ResourceRegistryStandardImpl   : HHH000387: ResultSet's statement was not registered
DEBUG 5804 --- [           main] .l.e.p.AbstractLoadPlanBasedEntityLoader : Done entity load : com.vergilyn.examples.entity.Customer#1
DEBUG 5804 --- [           main] stomAnnotationTransactionAttributeSource : Adding transactional method 'save' with attribute: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Found thread-bound EntityManager [SessionImpl(1589864603<open>)] for JPA transaction
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Participating in existing transaction
DEBUG 5804 --- [           main] o.h.e.i.DefaultMergeEventListener        : EntityCopyObserver strategy: disallow

DEBUG 5804 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL update
DEBUG 5804 --- [           main] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL statement [update customer set email = ? where customer_id = ?]

DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Initiating transaction commit
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Committing JPA transaction on EntityManager [SessionImpl(1589864603<open>)]
DEBUG 5804 --- [           main] o.h.e.t.internal.TransactionImpl         : committing
DEBUG 5804 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Processing flush-time cascades
DEBUG 5804 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Dirty checking collections
DEBUG 5804 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 insertions, 1 updates, 0 deletions to 1 objects
DEBUG 5804 --- [           main] o.h.e.i.AbstractFlushingEventListener    : Flushed: 0 (re)creations, 0 updates, 0 removals to 0 collections
DEBUG 5804 --- [           main] o.hibernate.internal.util.EntityPrinter  : Listing entities:
DEBUG 5804 --- [           main] o.hibernate.internal.util.EntityPrinter  : com.vergilyn.examples.entity.Customer{firstName=MARY, lastName=SMITH, lastUpdate=2019-03-21 20:55:20.0, customerId=1, active=1, storeId=1, email=before, addressId=5, createDate=2006-02-14 22:04:36.0}
DEBUG 5804 --- [           main] org.hibernate.SQL                        : update customer set active=?, address_id=?, create_date=?, email=?, first_name=?, last_name=?, last_update=?, store_id=? where customer_id=?
Hibernate: update customer set active=?, address_id=?, create_date=?, email=?, first_name=?, last_name=?, last_update=?, store_id=? where customer_id=?
DEBUG 5804 --- [           main] o.s.orm.jpa.JpaTransactionManager        : Closing JPA EntityManager [SessionImpl(1589864603<open>)] after transaction
```
由此可知，jdbc其实先于jpa提交，所以结果会被jpa覆盖。    
    

总结：
  所以，最好不要在一个事务中混用jpa+jdbc，可能因为session-cache而造成不一样的结果。
  
**如何关闭jpa/hibernate的session缓存: google/baidu并未找到禁用的方法，貌似无法禁用**



## 3. `failed to lazily initialize a collection of role: c...UserEntity.orders, could not initialize proxy - no Session`
```java
public class UserEntity extends AbstractEntity<Long>{

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private List<UserOrderEntity> orders;
	
	// ...
}
```

改成`FetchType.EAGER`解决，**但不满足期望**。