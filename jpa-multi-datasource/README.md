# jpa-multi-datasource

## 缺陷

1. 增加数据源
当需要增加数据源时，不管要新增`yaml`配置，还需要新增`XxxDatasourceConfiguration`，且代码完全一直。

2. 如何配置name-strategy？
在single-datasource的默认配置下，并不需要显示的指定`@Column(name = "customer_id")`。
在multi-datasource中，如果不显示声明，jpa会`unknown column "customerId"`。

- [Jpa多数据源，为每个数据源配置单独的命名策略](https://www.jianshu.com/p/1a4a35bcf0f6)
```
spring:
  jpa:
    hibernate:
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
        implicit-strategy: org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
```