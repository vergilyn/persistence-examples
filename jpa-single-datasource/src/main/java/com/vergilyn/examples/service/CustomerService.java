package com.vergilyn.examples.service;

import javax.transaction.Transactional;

import com.vergilyn.examples.entity.Customer;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface CustomerService {
    String ROLLBACK_MESSAGE = "transactional rollback!";

    static String email(String mark){
        return mark + "@" + System.currentTimeMillis();
    }

    Customer getByJpa(Long customerId);

    Customer getByJdbc(Long customerId);

    Customer getByNamed(Long customerId);

    Customer updateByJpa(Long customerId, String email);

    int updateByJdbc(Long customerId, String email);

    int updateByNamed(Long customerId, String email);

    void rollback();

    void rollback2();

    @Transactional
    void commitSequence();
}
