package com.vergilyn.examples;

import javax.transaction.Transactional;

import com.vergilyn.examples.config.dynamic.DynamicDataSourceAspect;
import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.service.CustomerService;

import org.aspectj.lang.JoinPoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDynamicDatasourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Value("${spring.application.name}")
    private String application;

    @Before
    public void before(){
        System.out.println("before >>>> " + application);
    }

    /**
     * excepted: output = "alpha - beta - alpha - beta - alpha" <br/>
     * actual:   output = "alpha - alpha- alpha - alpha - alpha" <br/>
     * remark: aop无法切入同类调用的方法
     */
    @Test
    public void queryByInnerPolling(){
        String output = customerService.queryByInnerPolling();
        Assert.assertThat(output, allOf(containsString("alpha"), containsString("beta")));
    }

    /**
     * excepted: output = "alpha - beta - alpha - beta - alpha" <br/>
     * actual:   output = "alpha - beta - alpha - beta - alpha" <br/>
     * remark: 不处于同一事务，也不是相同类调用。每次调用均会轮询选择datasource
     */
    @Test
    public void queryNoTransactional(){
        String output = queryPolling();
        System.out.println("queryNoTransactional >>>> " + output);
        Assert.assertThat(output, allOf(containsString("alpha"), containsString("beta")));
    }

    /**
     * excepted: output = "master - master - master - master - master" <br/>
     * actual:   output = "master - master - master - master - master" <br/>
     * remark:
     *   开启事务，默认使用master-datasource。（注: service#method并未被@Transaction修饰）
     *   通过输出的信息，可以知道AOP生效。
     *   但是并没有`DynamicRoutingDataSource#determineCurrentLookupKey()`的输出，猜测如果junit-test开启事务并不支持route-datasource。
     */
    @Test
    @Transactional
    public void queryTransactional(){
        System.out.println("queryTransactional >>>> start...");

        String output = queryPolling();
        System.out.println("queryTransactional >>>> " + output);
        Assert.assertThat(output, allOf(not(containsString("alpha")), not(containsString("beta"))));
    }

    /**
     * excepted: master <br/>
     * actual:  master <br/>
     * remark:
     *   如果service#method显示声明`@Transaction`，则AOP不进行拦截（datasource默认使用master）。
     *   <pre>
     *       default-datasource实际取自DynamicDataSourceContextHolder#CONTEXT_HOLDER，因为AOP在方法执行完后会重置，所以默认使用的是master。
     *   </pre>
     * @see DynamicDataSourceAspect#restoreDataSource(JoinPoint) - AOP在service#method执行完后，重置datasource
     */
    @Test
    public void getByTransaction(){
        Customer slaver = customerService.getByJpa(1L);  // AOP在方法调用完后会restore-datasource。
        Customer master = customerService.getByTransaction();  // transaction不走AOP，所以用default-datasource
        System.out.printf("getByTransaction >>>> slaver: %s, master: %s \n", slaver.getEmail(), master.getEmail());
        Assert.assertEquals("master", master.getEmail());
    }

    private String queryPolling(){
        Long id = 1L;
        Customer first = customerService.getByJpa(id);
        Customer second = customerService.getByJdbc(id);
        Customer third = customerService.getByNamed(id);
        Customer fourth = customerService.getByJpa(id);
        Customer fifth = customerService.getByJdbc(id);

        return String.format("first: %s, second: %s, third: %s, fourth: %s, fifth: %s \n",
                first.getEmail(), second.getEmail(), third.getEmail(), fourth.getEmail(), fifth.getEmail());
    }

}
