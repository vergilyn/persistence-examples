# jpa-dynamic-datasource

- [spring-boot-dynamic-datasource](https://github.com/helloworlde/SpringBoot-DynamicDataSource)
- [java 使用spring实现读写分离](https://www.cnblogs.com/fengwenzhee/p/7193218.html?utm_source=itdadao&utm_medium=referral)
- [Spring(AbstractRoutingDataSource)实现动态数据源切换](https://www.cnblogs.com/xiaochunqiu/p/7813276.html)
- [使用Spring的AbstractRoutingDataSource实现多数据源切换](https://www.cnblogs.com/weknow619/p/6415900.html)

## 实现
重写`org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()`。
AOP更灵活的配置datasource的采用规则。

slaver采用轮询: 1. 某个slaver不可用，切换成master  2. 某个slaver不可用，切换成下一个slaver，所有slaver都不可用时，才切换成master
  代码参考：感觉各个demo中的太简单，可以去看下ribbon中怎么实现的负载均衡


优化：**为了更灵活，可以考虑引入自定义注解来指定datasource（不再单纯通过 method-prefix判断）**


0. 侵入业务？
虽然基于AOP+自定义注解，可以灵活的，较少的侵入业务。
但对service的方法命名有约束（当然这不是问题）

是否存在另外一种方案，在底层通过解析sql判断是write/read，然后选择数据库？
但这可能要特别小心事务的一致性。

0. service/dao 时AOP切入判断数据源选择？
一般是在service层添加事务，所以感觉更优的选择是在service切入，而不是dao。

1. 事务一致性？
方案：数据源的抉择发生在service，而不是dao。保证一个service-method用一个datasource，而不是一个dao-method使用一个datasource。


如果一个service-method包含读写，则只使用master-datasource。

如果一个service-method包含多个读， 是否只应该用某一个slaver-datasource（而不是每个读 都判断选择slaver-datasource）？


2. 基于nacos 配置中心，动态配置主从数据源
- 获取注册中心的配置
- 动态更新数据源配置
- 动态新增slaver


## 备注

### 1. 基于AOP拦截切换datasource
同`@Transactional`存在一样的问题，**AOP无法切入同类调用的方法**。

```
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public void queryByInnerPolling(){
        pollingQuery();
    }

    private void pollingQuery(){
        Long id = 1L;
        Customer first = getByJpa(id);
        Customer second = getByJdbc(id);
        Customer third = getByNamed(id);
        Customer fourth = getByJpa(id);
        Customer fifth = getByJdbc(id);

        System.out.printf("first: %s, second: %s, third: %s, fourth: %s, fifth: %s \n",
                first.getEmail(), second.getEmail(), third.getEmail(), fourth.getEmail(), fifth.getEmail());
    }
    
    // 省略部分代码: getByJpa()、getByJdbc()、getByNamed()

}

```
例如以上代码，
expected: 轮询查询slaver-datasource。 
  `output: alpha - beta - alpha - beta - alpha`

actual: 只会在调用`getPollingNoTransaction()`时走AOP决定使用的datasource，调用同类中的方法不会触发AOP。
  `output: alpha - alpha- alpha - alpha - alpha`