# persistence-examples

- [Spring-Data-JPA-Examples Github](https://github.com/spring-projects/spring-data-examples/tree/master/jpa)
- [Spring-Data-Book-Examples Github](https://github.com/spring-projects/spring-data-book/tree/master/jpa)
- [spring-boot-multiple-datasource](https://github.com/jahe/spring-boot-multiple-datasources)
- [spring-boot-dynamic-datasource](https://github.com/helloworlde/SpringBoot-DynamicDataSource)
- []

1. 数据源连接池选择: tomcat、hikari、dbcp2、Druid
推荐选择hikari/druid。
- [spring-boot-2.x配置Druid数据源及监控](https://www.cnblogs.com/sunny3096/p/9884648.html)
- [spring-boot默认数据库连接连接池 hikari](https://blog.csdn.net/qq_18860653/article/details/85234486)
- [spring-boot多数据源（三种数据库连接池--JDBC，dbcp2，Druid）](https://www.cnblogs.com/wzk-0000/p/9544432.html)

2. spring-boot事务
[SpringBoot实战笔记：24_Spring Boot 的事务支持](https://blog.csdn.net/android_zyf/article/details/79607733)
[SpringBoot系列: JdbcTemplate 事务控制](https://www.cnblogs.com/harrychinese/p/SpringBoot_jdbc_transaction.html)

3. 多数据源配置
- [springboot多数据源配置](https://www.cnblogs.com/fengmao/p/7538854.html)
- [spring-boot-multiple-datasource Github](https://github.com/jahe/spring-boot-multiple-datasources)
- [spring-boot-dynamic-datasource Github](https://github.com/helloworlde/SpringBoot-DynamicDataSource)


## 部分配置
```
# 单一数据源配置
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource # 全限定名，连接池。默认自动检测classpath
    continue-on-error: false # 初始化数据库的时候，如果错误，是否继续启动。
    sql-script-encoding: UTF-8 #sql脚本字符
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: XxxxxDatabase
      max-lifetime: 1800000
      connection-timeout: 10000
      connection-test-query: SELECT 1
  jdbc:
    template:
      # 设置底层的ResultSet从数据库返回的最大行数
      max-rows: -1
      # 设置底层的ResultSet每次从数据库返回的行数。该属性对程序的影响很大，如果设置过大，
      # 因为一次性载入的数据都放到内存中，所以内存消耗很大；反之相反。
      # 默认为-1，取驱动程序的默认值
      fetch-size: -1
      # 设置JdbcTemplate所创建的Statement查询数据时的最大超时时间
      query-timeout: 10s
```