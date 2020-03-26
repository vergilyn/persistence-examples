package com.vergilyn.examples.controller;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author VergiLyn
 * @date 2019-03-22
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("update")
    public void update(){
        customerService.updateByJpa(1L, CustomerService.email("jpa"));
        customerService.updateByJdbc(2L, CustomerService.email("jdbc"));
        customerService.updateByNamed(3L, CustomerService.email("named"));
    }

    @RequestMapping("/rollback")
    public void rollbackCatchScopeCommit(){
        Long id = 1L;

        Customer customer = customerService.getByJpa(id);
        log.info("before test >>>> last-update: {}", customer.getLastUpdate());

        log.info("prepare execute test-method");
        customerService.rollbackCatchScopeCommit(id);
        log.info("after execute test-method");

        customer = customerService.getByJpa(id);
        log.info("after test-method >>>> last-update: {}", customer.getLastUpdate());
    }
}
