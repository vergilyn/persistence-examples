package com.vergilyn.examples.repository;

import com.vergilyn.examples.entity.Customer;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface CustomCustomerRepository {
    Customer getByJdbc(Long customerId);

    Customer getByNamed(Long customerId);

    int updateByJdbc(Long customerId, String email);

    int updateByNamed(Long customerId, String email);
}
