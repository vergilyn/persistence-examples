package com.vergilyn.examples;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.service.CustomerService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaMultiDatasourceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerServiceTest {
    @Autowired
    @Qualifier("firstlyCustomerService")
    private CustomerService firstlyService;
    @Value("${spring.application.name}")
    private String application;

    @Before
    public void before(){
        System.out.println("before >>>> " + application);
    }

    @Test
    public void get(){
        Customer jpa = firstlyService.getByJpa(1L);
        Customer jdbc = firstlyService.getByJdbc(2L);
        Customer named = firstlyService.getByNamed(3L);

        System.out.printf("jpa >>>> %s \n\n", jpa);
        System.out.printf("jdbc >>>> %s \n\n", jdbc);
        System.out.printf("named >>>> %s \n\n", named);
    }

    @Test
    public void update(){
        firstlyService.updateByJpa(1L, CustomerService.email("jpa"));
        firstlyService.updateByJdbc(2L, CustomerService.email("jdbc"));
        firstlyService.updateByNamed(3L, CustomerService.email("named"));
    }

    @Test
    public void rollback(){
        try {
            firstlyService.rollback();
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(), CustomerService.ROLLBACK_MESSAGE);
        }
    }

    @Test
    public void commitSequence(){
        firstlyService.commitSequence();
    }
}
