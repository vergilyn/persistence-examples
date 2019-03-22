package com.vergilyn.examples;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.service.CustomerService;

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
    public void get(){
        Customer jpa = customerService.getByJpa(1L);
        Customer jdbc = customerService.getByJdbc(2L);
        Customer named = customerService.getByNamed(3L);

        System.out.printf("jpa >>>> %s \n\n", jpa);
        System.out.printf("jdbc >>>> %s \n\n", jdbc);
        System.out.printf("named >>>> %s \n\n", named);
    }

    @Test
    public void update(){
        customerService.updateByJpa(1L, CustomerService.email("jpa"));
        customerService.updateByJdbc(2L, CustomerService.email("jdbc"));
        customerService.updateByNamed(3L, CustomerService.email("named"));
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
    public void commitSequence(){
        customerService.commitSequence();
    }
}
