package com.vergilyn.examples;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaSingleDatasourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Value("${spring.application.name}")
    private String application;

    @Before
    public void before(){
        System.out.println("before >>>> " + application);
    }


    @Test
    public void rollback(){
        try {
            customerService.rollback();
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(), CustomerService.ROLLBACK_MESSAGE);
        }
    }

    @Test
    public void rollback2(){
        try {
            customerService.rollback2();
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(), CustomerService.ROLLBACK_MESSAGE);
        }
    }

    @Test
    public void rollbackCatchScopeCommit(){
        Long id = 1L;

        Customer customer = customerService.getByJpa(id);
        log.info("before test >>>> last-update: {}", customer.getLastUpdate());

        log.info("prepare execute test-method");
        try {
            customerService.rollbackCatchScopeCommit(id);
        } catch (Exception e) {
            // do nothing
        }
        log.info("after execute test-method");

        customer = customerService.getByJpa(id);
        log.info("after test-method >>>> last-update: {}", customer.getLastUpdate());

    }

    @Test
    public void commitSequence(){
        customerService.commitSequence();
    }
}
