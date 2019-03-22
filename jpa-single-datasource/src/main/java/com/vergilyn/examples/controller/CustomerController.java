package com.vergilyn.examples.controller;

import com.vergilyn.examples.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author VergiLyn
 * @date 2019-03-22
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping("update")
    public void update(){
        customerService.updateByJpa(1L, CustomerService.email("jpa"));
        customerService.updateByJdbc(2L, CustomerService.email("jdbc"));
        customerService.updateByNamed(3L, CustomerService.email("named"));
    }
}
